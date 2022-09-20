package com.example.mall.controller;

import cn.hutool.core.util.StrUtil;
import com.example.mall.constant.LoginCommonValue;
import com.example.mall.constant.ResultMessage;
import com.example.mall.entity.UserInfoEntity;
import com.example.mall.service.MessageSendService;
import com.example.mall.service.UserInfoService;
import com.example.mall.utils.JwtUtil;
import com.example.mall.vo.R;
import com.example.mall.vo.UserLoginVo;
import com.example.mall.vo.UserRegisterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.properties.EmailProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;


@Api(tags = "普通用户登录管理")
@Slf4j
@RestController
@RequestMapping("/simpleUser")
public class LoginController {
	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private MessageSendService messageSendService;

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@ApiOperation("注册获取验证码")
	@PostMapping("/getMsCode")
	public R getMessageCode(@RequestBody UserRegisterVo userRegisterVo) {
		String email = userRegisterVo.getEmail();
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
	public R register(@RequestBody UserRegisterVo userRegisterVo) {
		String code = userRegisterVo.getCode();
		String emailCode = stringRedisTemplate.opsForValue().get(LoginCommonValue.PREFIX_SMS_CODE + userRegisterVo.getEmail());
		if (!code.equals(emailCode)){
			return R.error().setMessage("验证码错误");
		}
		boolean success = userInfoService.addUser(userRegisterVo);
		if (success) {
			return R.ok();
		} else {
			return R.error(ResultMessage.ADD_USER_ERROR);
		}
	}

	@ApiOperation("用户登录")
	@PostMapping("/login")
	public R login(@RequestBody UserLoginVo userLoginVo) {
		log.info("用户登录");
		String token = userInfoService.login(userLoginVo);
		if (StringUtils.hasText(token)){
			return R.ok().setData(token);
		}else {
			return R.error(ResultMessage.LOGIN_ERROR);
		}
	}
	// 根据token获取用户信息
	@GetMapping("/getUserInfo")
	public R getUserInfo(HttpServletRequest request){
		log.info("获取用户信息");
		String userId = JwtUtil.getUserIdByJwtToken(request);
		if (StringUtils.hasText(userId)){
			UserInfoEntity userInfo = userInfoService.getById(userId);
			if (userInfo!=null){
				return R.ok().setData(userInfo);
			}else {
				return R.error().setMessage("登录信息无效");
			}
		}else {
			return R.error().setMessage("请先登录");
		}

	}
}
