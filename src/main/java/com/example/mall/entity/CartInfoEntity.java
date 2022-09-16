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
@TableName("cart_info")
@ApiModel(value = "CartInfoEntity对象", description = "购物车")
public class CartInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	@ApiModelProperty("购物车id")
	private String id;
	/**
	 * 用户id
	 */
	@ApiModelProperty("用户id")
	private String userId;
	/**
	 * 商品id
	 */
	@ApiModelProperty("商品id")
	private String productId;
	/**
	 * 商品数量
	 */
	@ApiModelProperty("商品数量")
	private Integer number;

}
