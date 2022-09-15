package com.example.mall.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
public class ProductInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String id;
	/**
	 * 商品类型
	 */
	private String type;
	/**
	 * 商品标题
	 */
	private String name;
	/**
	 * 描述
	 */
	private String describe;
	/**
	 * 商品主图
	 */
	private List<String> picture;
	/**
	 * 价格
	 */
	private Integer price;
	/**
	 * 商家id
	 */
	private String userId;

}
