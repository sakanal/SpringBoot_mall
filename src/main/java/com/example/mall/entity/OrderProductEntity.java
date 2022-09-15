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
@TableName("order_product")
public class OrderProductEntity implements Serializable {
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
	 * 商品号
	 */
	private String productId;
	/**
	 * 商品数量
	 */
	private Integer number;

}
