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
		//获取分页参数
		Integer current = params.get("current") == null ? 1 : new Integer(params.get("current").toString());
		//构建分页参数
		Page<ProductInfoEntity> page = new Page<ProductInfoEntity>(current, SelectArg.PAGESIZE);
		//预留条件构造
		QueryWrapper<ProductInfoEntity> wrapper = new QueryWrapper<>();
		//执行查询
		Page<ProductInfoEntity> ProductInfoPage = this.baseMapper.selectPage(page, wrapper);
		return ProductInfoPage;
	}

	@Override
	public Page<ProductInfoEntity> getPage(Integer current, ProductQuery productQuery) {
		//设置分页参数
		Page<ProductInfoEntity> page = new Page<>(current, SelectArg.PAGESIZE);
		QueryWrapper<ProductInfoEntity> queryWrapper = new QueryWrapper<>();
		if (productQuery != null) {
			//获取相关查询参数
			Long catId = productQuery.getCatId();
			String name = productQuery.getName();
			Integer price = productQuery.getPrice();
			String userId = productQuery.getUserId();
			Integer isRecommend = productQuery.getIsRecommend();
			if (StringUtils.hasText(name))
				queryWrapper.like("name", name);
			if (!StringUtils.isEmpty(catId))
				queryWrapper.eq("cat_id", catId);
			if (!StringUtils.isEmpty(price))
				queryWrapper.le("price", price);
			if (StringUtils.hasText(userId))
				queryWrapper.eq("user_id", userId);
			if (!StringUtils.isEmpty(isRecommend))
				queryWrapper.eq("is_recommend",isRecommend);
		}
		//执行查询
		this.page(page, queryWrapper);
		return page;
	}

	@Override
	public List<ProductInfoEntity> getProductsByUserId(String id) {
		return this.list(new QueryWrapper<ProductInfoEntity>().eq(StrUtil.isBlank(id), "user_id", id));
	}

	@Override
	public List<ProductInfoEntity> getRandomProduct(Long size) {
		//如果size为0或者null会获取全部的商品信息
		if (size == null || new Long(0L).equals(size)) {
			return this.list();
		}
		//去数据库随机拿size条数据，数据库数据不够size就是类似于返回所有
		List<ProductInfoEntity> list = this.list(new QueryWrapper<ProductInfoEntity>().last(" order by rand()"+"limit " + size ));
		return list;
	}

	/**
	 * 随机获取size个推荐商品，推荐不足size直接将所有推荐返回
	 * @param size
	 * @return
	 */
	@Override
	public List<ProductInfoEntity> randomGetRecommendProduct(Long size) {
		QueryWrapper<ProductInfoEntity> wrapper = new QueryWrapper<ProductInfoEntity>().eq("is_recommend", 1);
		if (size != null && !new Long(0L).equals(size)) {
			//构建查询条件
			wrapper.last("order by rand()"+" limit " + size);
		}
		//执行查询
		List<ProductInfoEntity> list = this.list(wrapper);

		return list;
	}

}
