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
import org.w3c.dom.stylesheets.LinkStyle;

/**
 *
 *
 * @author ouyang
 * @email wanbzoy@163.com
 * @date 2022-09-15 14:39:53
 */
@Data
@TableName("product_info")
@ApiModel(value = "ProductInfoEntity对象", description = "商品信息")
public class ProductInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	@ApiModelProperty("商品信息id")
	private String id;
	/**
	 * 商品类型
	 */
	@ApiModelProperty("商品类型")
	private Long catId;
	/**
	 * 商品标题
	 */
	@ApiModelProperty("商品标题")
	private String name;
	/**
	 * 描述
	 */
	@ApiModelProperty("描述")
	private String description;
	/**
	 * 商品主图
	 */
	@ApiModelProperty("商品主图")
	private String picture;
	/**
	 * 价格
	 */
	@ApiModelProperty("价格")
	private Integer price;
	/**
	 * 商家id
	 */
	@ApiModelProperty("商家id")
	private String userId;

	/**
	 * (冗余字段)
	 * 用于记录订单详情的数量
	 */
	@TableField(exist = false)
	private Integer number;
}
