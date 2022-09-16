package com.example.mall.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 *
 * @author ouyang
 * @email wanbzoy@163.com
 * @date 2022-09-15 14:39:53
 */
@Data
@TableName("user_address")
@ApiModel(value = "UserAddressEntity对象", description = "用户地址")
public class UserAddressEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	@ApiModelProperty("id")
	private String id;
	/**
	 * 用户id
	 */
	@ApiModelProperty("用户id")
	private String userId;
	/**
	 * 收货人姓名
	 */
	@ApiModelProperty("收货人姓名")
	private String name;
	/**
	 * 收货人电话
	 */
	@ApiModelProperty("收货人电话")
	private String phone;
	/**
	 * 收获地址
	 */
	@ApiModelProperty("收获地址")
	private String address;

}
