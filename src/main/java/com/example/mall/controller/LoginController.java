package com.example.mall.controller;

import cn.hutool.core.util.StrUtil;
import com.example.mall.constant.ResultMessage;
import com.example.mall.entity.UserInfoEntity;
import com.example.mall.service.UserInfoService;
import com.example.mall.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private UserInfoService userInfoService;

	@GetMapping("/getMsCode")
	public R getMessageCode(String email) {
		if (StrUtil.isBlank(email)) {
			return R.error(ResultMessage.MISSING_PARAMETERS);
		} else {
			//TODO 发送验证码业务
			return R.ok();
		}
	}

	@PostMapping("/register")
	public R register(@RequestBody UserInfoEntity user) {
		boolean success = userInfoService.addUser(user);
		if (success) {
			return R.ok();
		} else {
			return R.error(ResultMessage.ADD_USER_ERROR);
		}
	}

	@PostMapping
	public R login(@RequestBody UserInfoEntity user) {
		boolean success = userInfoService.index(user);
		if (success){
			return R.ok();
		}else {
			return R.error(ResultMessage.LOGIN_ERROR);
		}
	}
}
