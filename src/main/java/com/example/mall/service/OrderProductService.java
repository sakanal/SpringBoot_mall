package com.example.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.entity.OrderProductEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * 
 *
 * @author ouyang
 * @email wanbzoy@163.com
 */
public interface OrderProductService extends IService<OrderProductEntity> {

	Page<OrderProductEntity> getPage(Map<String, Object> params);

    void removeByProductIds(String[] ids);

    void removeByOrderIds(String[] ids);
}

