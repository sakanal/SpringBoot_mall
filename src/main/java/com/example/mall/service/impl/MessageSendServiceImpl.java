package com.example.mall.service.impl;

import com.example.mall.service.MessageSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MessageSendServiceImpl implements MessageSendService {
	@Autowired
	private JavaMailSender javaMailSender;
	@Value("${spring.mail.username}")
	private String fromCount;
	@Override
	public void sendMsg(String email, String codeNum) {
		SimpleMailMessage message=new SimpleMailMessage();
		message.setFrom(fromCount);
		message.setTo(email);
		message.setSubject("在线商城注册验证码");
		message.setText(codeNum);
		javaMailSender.send(message);
	}
}
