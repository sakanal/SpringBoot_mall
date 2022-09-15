package com.example.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.entity.CategoryInfoEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author ouyang
 * @email wanbzoy@163.com
 */
public interface CategoryInfoService extends IService<CategoryInfoEntity> {

	Page<CategoryInfoEntity> getPage(Map<String, Object> params);

	List<CategoryInfoEntity> listWithTree();
}

