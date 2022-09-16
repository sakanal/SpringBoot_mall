package com.example.mall.service;

import org.springframework.stereotype.Service;

@Service
public interface MessageSendService {

	void sendMsg(String email, String codeNum);
}
