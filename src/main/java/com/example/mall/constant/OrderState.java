package com.example.mall.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum OrderState {
	NEW("新建",0),
	PAID("已付款",1),
	DELIVERED("已发货",2),
	RECEIVED("已收货",3),
	ClOSE("订单已关闭",4),;
	private final String state;
	private final Integer code;
}
