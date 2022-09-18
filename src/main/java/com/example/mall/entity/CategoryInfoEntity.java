package com.example.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 商品三级分类
 *
 * @author ouyang
 * @email wanbzoy@163.com
 * @date 2022-09-15 14:39:53
 */
@Data
@TableName("category_info")
@ApiModel(value = "CategoryInfoEntity对象", description = "商品类别")
public class CategoryInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类id
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty("分类id")
	private Long catId;
	/**
	 * 分类名称
	 */
	@ApiModelProperty("分类名称")
	private String name;
	/**
	 * 父分类id
	 */
	@ApiModelProperty("父分类id")
	private Long parentCid;
	/**
	 * 层级
	 */
	@ApiModelProperty("层级")
	private Integer catLevel;
	/**
	 * 是否显示[0-不显示，1显示]
	 */
	@ApiModelProperty("是否显示[0-不显示，1显示]")
	private Integer showStatus;
	/**
	 * 排序
	 */
	@ApiModelProperty("排序")
	private Integer sort;

	@TableField(exist = false)
	@ApiModelProperty("子类别列表")
	private List<CategoryInfoEntity> children;

}
