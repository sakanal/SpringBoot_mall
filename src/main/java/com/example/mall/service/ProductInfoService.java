package com.example.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.entity.ProductInfoEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.vo.ProductQuery;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author ouyang
 * @email wanbzoy@163.com
 */
public interface ProductInfoService extends IService<ProductInfoEntity> {

	Page<ProductInfoEntity> getPage(Map<String, Object> params);

	Page<ProductInfoEntity> getPage(Integer current, ProductQuery productQuery);

	List<ProductInfoEntity> getProductsByUserId(String id);

	List<ProductInfoEntity> getRandomProduct(Long size);

    List<ProductInfoEntity> randomGetRecommendProduct(Long size);
}

