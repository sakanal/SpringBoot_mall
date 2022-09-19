package com.example.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.SelectArg;
import com.example.mall.vo.ProductQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.mall.dao.ProductInfoDao;
import com.example.mall.entity.ProductInfoEntity;
import com.example.mall.service.ProductInfoService;
import org.springframework.util.StringUtils;


@Service("productInfoService")
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoDao, ProductInfoEntity> implements ProductInfoService {

	@Override
	public Page<ProductInfoEntity> getPage(Map<String, Object> params) {
		Integer current = params.get("current") == null ? 1 : new Integer(params.get("current").toString());
		Page<ProductInfoEntity> page = new Page<ProductInfoEntity>(current, SelectArg.PAGESIZE);
		QueryWrapper<ProductInfoEntity> wrapper = new QueryWrapper<>();
		Page<ProductInfoEntity> ProductInfoPage = this.baseMapper.selectPage(page, wrapper);
		return ProductInfoPage;
	}

	@Override
	public Page<ProductInfoEntity> getPage(Integer current, ProductQuery productQuery) {
		Page<ProductInfoEntity> page = new Page<>(current, SelectArg.PAGESIZE);
		QueryWrapper<ProductInfoEntity> queryWrapper = new QueryWrapper<>();
		if (productQuery != null) {
			Long cat_id = productQuery.getCat_id();
			String name = productQuery.getName();
			Integer price = productQuery.getPrice();
			String userId = productQuery.getUserId();
			if (StringUtils.hasText(name))
				queryWrapper.like("name", name);
			if (!StringUtils.isEmpty(cat_id))
				queryWrapper.eq("cat_id", cat_id);
			if (!StringUtils.isEmpty(price))
				queryWrapper.le("price", price);
			if (StringUtils.hasText(userId))
				queryWrapper.eq("user_id", userId);
		}
		this.page(page, queryWrapper);
		return page;
	}

	@Override
	public List<ProductInfoEntity> getProductsByUserId(String id) {
		return this.list(new QueryWrapper<ProductInfoEntity>().eq(StrUtil.isBlank(id), "user_id", id));
	}

	@Override
	public List<ProductInfoEntity> getRandomProduct(Long size) {
		if (size == null || new Long(0L).equals(size)) {
			return this.list();
		}
		List<ProductInfoEntity> list = this.list(new QueryWrapper<ProductInfoEntity>().last(" order by rand()"+"limit " + size ));
		return list;
	}

	@Override
	public List<ProductInfoEntity> randomGetRecommendProduct(Long size) {
		QueryWrapper<ProductInfoEntity> wrapper = new QueryWrapper<ProductInfoEntity>().eq("is_recommend", 1);
		if (size != null && !new Long(0L).equals(size)) {
			wrapper.last("order by rand()"+" limit " + size);
		}
		List<ProductInfoEntity> list = this.list(wrapper);

		return list;
	}

}
