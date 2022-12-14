package com.example.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.entity.OrderInfoEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.vo.OrderInfoVo;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author ouyang
 * @email wanbzoy@163.com
 */
public interface OrderInfoService extends IService<OrderInfoEntity> {

	Page<OrderInfoEntity> getPage(Map<String, Object> params);

	OrderInfoEntity getByOrderId(String orderId);

	List<OrderInfoEntity> getProductsByUserId(String id);

	void saveOrder(List<OrderInfoVo> orderInfoList);

	Page<OrderInfoEntity> getPage(Integer current, OrderInfoEntity orderInfoEntity);

	void removeByOrderIds(String[] orderIds);
}

