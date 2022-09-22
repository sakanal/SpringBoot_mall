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

    @ApiOperation("登录")
    @PostMapping("/login")
    public R login(@RequestBody UserInfoEntity userInfo){
        String userName = userInfo.getUserName();
        String password = userInfo.getPassword();
        if (!StringUtils.hasText(userName)){
            throw new MyException(20001,"未输入用户名");
        }
        if (!StringUtils.hasText(password)){
            throw new MyException(20001,"未输入密码");
        }
        QueryWrapper<UserInfoEntity> queryWrapper = new QueryWrapper<UserInfoEntity>().eq("role", 0);
        queryWrapper.eq("username",userName).eq("password",password);
        UserInfoEntity userInfoEntity = userInfoService.getOne(queryWrapper);
        if (userInfoEntity!=null){
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
