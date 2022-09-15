package com.example.mall.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author ouyang
 * @email wanbzoy@163.com
 * @date 2022-09-15 14:39:52
 */
@Data
@TableName("order_info")
public class OrderInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 订单号
	 */
	private String orderSn;
	/**
	 * 订单创建时间
	 */
	private Date createTime;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 订单状态0出单，1出货，2收货
	 */
	private Integer status;
	/**
	 * 发货时间
	 */
	private Date deliveryTime;
	/**
	 * 收货时间
	 */
	private Date receiveTime;
	/**
	 * 订单总额
	 */
	private Double totalAmount;
	/**
	 * 运费金额
	 */
	private Double freightAmount;
	/**
	 * 实付金额
	 */
	private Double payAmount;
	/**
	 * 
	 */
	private Integer addressId;

}
