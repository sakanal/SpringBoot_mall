package com.example.mall.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mall.entity.UserInfoEntity;
import com.example.mall.exception.MyException;
import com.example.mall.service.UserInfoService;
import com.example.mall.utils.JwtUtil;
import com.example.mall.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "临时登录")
@Slf4j
@RestController
@RequestMapping("/user")
public class AdminLoginController {
    @Resource
    private UserInfoService userInfoService;

    @ApiOperation("管理员登录")
    @PostMapping("/login")
    public R login(@RequestBody UserInfoEntity userInfo){
        // 获取登录的用户名
        String userName = userInfo.getUserName();
        // 获取登录的密码
        String password = userInfo.getPassword();
        // 判断用户名是否存在数据
        if (!StringUtils.hasText(userName)){
            throw new MyException(20001,"未输入用户名");
        }
        // 判断密码是否存在数据
        if (!StringUtils.hasText(password)){
            throw new MyException(20001,"未输入密码");
        }
        // 创建查询条件，用户身份为0（管理员），用户名和密码为前端传递过来的值
        QueryWrapper<UserInfoEntity> queryWrapper = new QueryWrapper<UserInfoEntity>().eq("role", 0);
        queryWrapper.eq("username",userName).eq("password",password);
        UserInfoEntity userInfoEntity = userInfoService.getOne(queryWrapper);
        // 如果从数据库中获取到的数据不会空，代表用户存在，且用户名和密码对应
        if (userInfoEntity!=null){
            // 根据用户id和用户名称生成对应的token登录凭证
            String userJwtToken = JwtUtil.getUserJwtToken(userInfoEntity.getId(), userInfoEntity.getUserName());
            Map<String, String> loginToken = new HashMap<>();
            loginToken.put("token",userJwtToken);
            return R.ok().setData(loginToken);
        }else {
            throw new MyException(20001,"用户不存在");
        }
    }

    @ApiOperation(value = "获取登录成功后的用户信息信息")
    @GetMapping("/info")
    public R info(@RequestParam("token")String token){
        String userIdByJwtToken = JwtUtil.getUserIdByJwtToken(token);
        UserInfoEntity userInfoEntity = userInfoService.getById(userIdByJwtToken);

        Map<String, String> info = new HashMap<>();
        info.put("name",userInfoEntity.getUserName());
        info.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return R.ok().setData(info);
    }


    @ApiOperation(value = "退出登录用户")
    @PostMapping("/logout")
    public R logout(){
        return R.ok();
    }

}
