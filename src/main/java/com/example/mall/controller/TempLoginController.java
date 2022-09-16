package com.example.mall.controller;

import com.example.mall.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "临时登录")
@Slf4j
@RestController
@RequestMapping("/user")
public class TempLoginController {
    @ApiOperation("登录")
    @PostMapping("/login")
    public R login(){
        Map<String, String> loginToken = new HashMap<>();
        loginToken.put("token","sakanal");
        return R.ok().setData(loginToken);
    }

    @ApiOperation(value = "获取登录成功后的用户信息信息")
    @GetMapping("/info")
    public R info(@RequestParam("token")String token){
        if ("sakanal".equals(token)){
            log.info("sakanal 正在登录");
        }
        Map<String, String> info = new HashMap<>();
//        info.put("roles","admin");
        info.put("name","sakanal");
        info.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return R.ok().setData(info);
    }


    @ApiOperation(value = "退出登录用户")
    @PostMapping("/logout")
    public R logout(){
        return R.ok();
    }

}
