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
 * @date 2022-09-15 14:39:52
 */
@Data
@TableName("order_product")
@ApiModel(value = "OrderProductEntity对象", description = "订单内商品")
public class OrderProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	@ApiModelProperty("订单内商品id")
	private String id;
	/**
	 * 订单号
	 */
	@ApiModelProperty("订单号")
	private String orderSn;
	/**
	 * 商品号
	 */
	@ApiModelProperty("商品号")
	private String productId;
	/**
	 * 商品数量
	 */
	@ApiModelProperty("商品数量")
	private Integer number;

}
