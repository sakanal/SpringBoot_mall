package com.example.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.SelectArg;
import com.example.mall.entity.OrderProductEntity;
import com.example.mall.entity.ProductInfoEntity;
import com.example.mall.service.OrderProductService;
import com.example.mall.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.mall.dao.OrderInfoDao;
import com.example.mall.entity.OrderInfoEntity;
import com.example.mall.service.OrderInfoService;


@Service("orderInfoService")
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoDao, OrderInfoEntity> implements OrderInfoService {

	@Autowired
	private OrderProductService orderProductService;

	@Autowired
	private ProductInfoService productInfoService;

    @Override
    public Page<OrderInfoEntity> getPage(Map<String, Object> params) {
		Integer current = params.get("current") == null ? 1 : new Integer(params.get("current").toString());
		Page<OrderInfoEntity> page=new Page<OrderInfoEntity>(current, SelectArg.PAGESIZE);
		QueryWrapper<OrderInfoEntity> wrapper = new QueryWrapper<>();
		Page<OrderInfoEntity> OrderInfoPage = this.baseMapper.selectPage(page, wrapper);
		return OrderInfoPage;
    }

	@Override
	public OrderInfoEntity getByOrderId(String orderId) {
		OrderInfoEntity orderInfoEntity = this.getOne(new QueryWrapper<OrderInfoEntity>().eq(!StrUtil.isBlank(orderId), "order_sn", orderId));
		List<OrderProductEntity> orderProdectList = orderProductService.list(new QueryWrapper<OrderProductEntity>()
				.eq(!StrUtil.isBlank(orderInfoEntity.getOrderSn()), "order_sn", orderInfoEntity.getOrderSn()));
		List<String> productIds = orderProdectList.stream().map(item -> {
			return item.getProductId();
		}).collect(Collectors.toList());
		List<ProductInfoEntity> productInfoEntities = productInfoService.listByIds(productIds);
		for (ProductInfoEntity productInfoEntity : productInfoEntities) {
			productInfoEntity.setNumber(getOrderProductNumber(productInfoEntity.getId(),orderProdectList));
		}
		orderInfoEntity.setProductList(productInfoEntities);
		return orderInfoEntity;
	}

	private Integer getOrderProductNumber(String id, List<OrderProductEntity> orderProdectList) {
		for (OrderProductEntity orderProductEntity : orderProdectList) {
			if (orderProductEntity.getId().equals(id)){
				return orderProductEntity.getNumber();
			}
		}
		return 0;
	}
}