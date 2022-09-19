package com.example.mall.controller;

import cn.hutool.core.util.StrUtil;
import com.example.mall.constant.LoginCommonValue;
import com.example.mall.constant.ResultMessage;
import com.example.mall.entity.UserInfoEntity;
import com.example.mall.service.MessageSendService;
import com.example.mall.service.UserInfoService;
import com.example.mall.vo.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.properties.EmailProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private MessageSendService messageSendService;

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@ApiOperation("注册获取验证码")
	@GetMapping("/getMsCode")
	public R getMessageCode(String email) {
		if (StrUtil.isBlank(email)) {
			return R.error(ResultMessage.MISSING_PARAMETERS);
		} else {
			//TODO 发送验证码业务
			int code = (int) ((Math.random() * 9 + 1) * 100000);
			String codeNum = String.valueOf(code);
			stringRedisTemplate.opsForValue().set(LoginCommonValue.PREFIX_SMS_CODE +email,
					codeNum,LoginCommonValue.LOGIN_CODE_EXPIRE, TimeUnit.MINUTES);
			messageSendService.sendMsg(email,codeNum);
			return R.ok();
		}
	}


	@ApiOperation("注册")
	@PostMapping("/register")
	public R register(String code,@RequestBody UserInfoEntity user) {
		String emailCode = stringRedisTemplate.opsForValue().get(LoginCommonValue.PREFIX_SMS_CODE + user.getEmail());
		if (!code.equals(emailCode)){
			return R.error().setMessage("验证码错误");
		}
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
