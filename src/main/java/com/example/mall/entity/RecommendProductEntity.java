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
@TableName("recommend_product")
@ApiModel(value = "RecommendProductEntity对象", description = "推荐商品")
public class RecommendProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	@ApiModelProperty("id")
	private String id;
	/**
	 * 商品id
	 */
	@ApiModelProperty("商品id")
	private String productId;

}
