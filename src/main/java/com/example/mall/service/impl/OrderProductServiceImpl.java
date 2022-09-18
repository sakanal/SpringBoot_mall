package com.example.mall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.SelectArg;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.mall.dao.OrderProductDao;
import com.example.mall.entity.OrderProductEntity;
import com.example.mall.service.OrderProductService;


@Service("orderProductService")
public class OrderProductServiceImpl extends ServiceImpl<OrderProductDao, OrderProductEntity> implements OrderProductService {

    @Override
    public Page<OrderProductEntity> getPage(Map<String, Object> params) {
		Integer current = params.get("current") == null ? 1 : new Integer(params.get("current").toString());
		Page<OrderProductEntity> page=new Page<OrderProductEntity>(current, SelectArg.PAGESIZE);
		QueryWrapper<OrderProductEntity> wrapper = new QueryWrapper<>();
		Page<OrderProductEntity> OrderProductPage = this.baseMapper.selectPage(page, wrapper);
		return OrderProductPage;
    }

	@Override
	public void removeByProductIds(String[] ids) {
		this.remove(new QueryWrapper<OrderProductEntity>().in("product_id",ids));
	}

}