package com.example.mall.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.ResultMessage;
import com.example.mall.vo.UserInfoQuery;
import com.example.mall.vo.UserPasswordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.mall.entity.UserInfoEntity;
import com.example.mall.service.UserInfoService;
import com.example.mall.vo.R;



/**
 *
 *
 * @author ouyang
 * @email wanbzoy@163.com
 */

@Api(tags = "用户信息管理")
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        Page<UserInfoEntity> page = userInfoService.getPage(params);

        return R.ok().setData(page);
    }

    @ApiOperation("分页查询")
    @PostMapping("/pageFind/{current}")
    public R pageFind(@PathVariable("current")Integer current,
                      @RequestBody(required = false) UserInfoQuery userInfoQuery){
        Page<UserInfoEntity> page = userInfoService.getPage(current,userInfoQuery);
        if (page!=null){
            return R.ok().setData(page);
        }else {
            return R.error().setMessage("该条件下暂无数据");
        }
    }
    @ApiOperation("查询所有商家")
    @GetMapping("/findMerchant")
    public R pageFind(){
        List<UserInfoEntity> list = userInfoService.list(new QueryWrapper<UserInfoEntity>().eq("role", 1));
        if (list!=null){
            return R.ok().setData(list);
        }else {
            return R.error().setMessage("该条件下暂无数据");
        }
    }
    @ApiOperation("添加用户")
    @PostMapping("/addUser")
    public R addUser(@RequestBody UserInfoEntity userInfo){
        boolean save = userInfoService.save(userInfo);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 根据id查询信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
		UserInfoEntity userInfo = userInfoService.getById(id);

        return R.ok().setData(userInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody UserInfoEntity userInfo){
		userInfoService.save(userInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody UserInfoEntity userInfo){
		userInfoService.updateById(userInfo);

        return R.ok();
    }

    @ApiOperation("修改密码")
    @PutMapping("/updatePassword")
    public R updatePassword(@RequestBody UserPasswordVo userPasswordVo){
        String token = userInfoService.updatePassword(userPasswordVo);
        if (token!=null){
            return R.ok().setData(token);
        }else {
            return R.error().setMessage("修改密码失败");
        }
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody String[] ids){
		userInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
