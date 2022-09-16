package com.example.mall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.SelectArg;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.mall.dao.CartInfoDao;
import com.example.mall.entity.CartInfoEntity;
import com.example.mall.service.CartInfoService;


@Service("cartInfoService")
public class CartInfoServiceImpl extends ServiceImpl<CartInfoDao, CartInfoEntity> implements CartInfoService {

	@Override
	public Page<CartInfoEntity> getPage(Map<String, Object> params) {
		Integer current = params.get("current") == null ? 1 : new Integer(params.get("current").toString());
		String key = params.get("key").toString();
		Page<CartInfoEntity> page = new Page<CartInfoEntity>(current, SelectArg.PAGESIZE);
		QueryWrapper<CartInfoEntity> wrapper = new QueryWrapper<>();
		wrapper.like(key == null, "user_id", key).or().like(key != null, "product_id", key);
		Page<CartInfoEntity> CartInfoPage = this.baseMapper.selectPage(page, wrapper);
		return CartInfoPage;
	}

}