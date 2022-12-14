package com.example.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.entity.RecommendProductEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author ouyang
 * @email wanbzoy@163.com
 */
public interface RecommendProductService extends IService<RecommendProductEntity> {

	Page<RecommendProductEntity> getPage(Map<String, Object> params);

	void removeBatchByProductIds(String[] ids);

	List<String> getRecommendProductIds(Long size);

    Boolean removeRecommend(String productId);

	Boolean setRecommend(String productId);
}

