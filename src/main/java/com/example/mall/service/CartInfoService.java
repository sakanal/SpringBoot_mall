package com.example.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.entity.CartInfoEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.entity.ProductInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author ouyang
 * @email wanbzoy@163.com
 */
public interface CartInfoService extends IService<CartInfoEntity> {

	Page<CartInfoEntity> getPage(Map<String, Object> params);

    void removeByProductIds(String[] ids);

	List<ProductInfoEntity> getProductByUserId(String userId);

	void updateByUserId(CartInfoEntity cartInfo);
}

