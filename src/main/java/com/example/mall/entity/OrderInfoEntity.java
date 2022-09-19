package com.example.mall.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "OrderInfoEntity对象", description = "订单")
public class OrderInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	@ApiModelProperty("订单id")
	private String id;
	/**
	 * 订单号
	 */
	@ApiModelProperty("订单号")
	private String orderSn;
	/**
	 * 订单创建时间
	 */
	@ApiModelProperty("订单创建时间")
	private Date createTime;
	/**
	 * 用户id
	 */
	@ApiModelProperty("用户id")
	private String userId;
	/**
	 * 订单状态0出单，1出货，2收货
	 */
	@ApiModelProperty("订单状态0出单，1出货，2收货")
	private Integer status;
	/**
	 * 发货时间
	 */
	@ApiModelProperty("发货时间")
	private Date deliveryTime;
	/**
	 * 收货时间
	 */
	@ApiModelProperty("收货时间")
	private Date receiveTime;
	/**
	 * 订单总额
	 */
	@ApiModelProperty("订单总额")
	private Double totalAmount;
	/**
	 * 运费金额
	 */
	@ApiModelProperty("运费金额")
	private Double freightAmount;
	/**
	 * 实付金额
	 */
	@ApiModelProperty("实付金额")
	private Double payAmount;
	/**
	 *
	 */
	@ApiModelProperty("地址id")
	private String addressId;

	@TableField(exist = false)
	List<ProductInfoEntity> productList;
}
