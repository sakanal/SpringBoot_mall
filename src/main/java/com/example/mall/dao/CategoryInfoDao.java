package com.example.mall.dao;

import com.example.mall.entity.CategoryInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author ouyang
 * @email wanbzoy@163.com
 * @date 2022-09-15 14:39:53
 */
@Mapper
public interface CategoryInfoDao extends BaseMapper<CategoryInfoEntity> {
	
}
