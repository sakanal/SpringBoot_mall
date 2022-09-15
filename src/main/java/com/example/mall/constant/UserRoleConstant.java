package com.example.mall.constant;

import lombok.Getter;

@Getter
public enum UserRoleConstant {
	ADMIN("管理员",0),
	SELLER("商家",1),
	CONSUMER("普通用户",2);
	private final String role;
	private final Integer code;

	UserRoleConstant(String role, Integer code) {
		this.role = role;
		this.code = code;
	}
}
