package com.example.mall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.SelectArg;
import com.example.mall.entity.ProductInfoEntity;
import com.example.mall.service.ProductInfoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.mall.dao.RecommendProductDao;
import com.example.mall.entity.RecommendProductEntity;
import com.example.mall.service.RecommendProductService;

import javax.annotation.Resource;


@Service("recommendProductService")
public class RecommendProductServiceImpl extends ServiceImpl<RecommendProductDao, RecommendProductEntity> implements RecommendProductService {
	@Resource
	private ProductInfoService productInfoService;

    @Override
    public Page<RecommendProductEntity> getPage(Map<String, Object> params) {
		Integer current = params.get("current") == null ? 1 : new Integer(params.get("current").toString());
		Page<RecommendProductEntity> page=new Page<RecommendProductEntity>(current, SelectArg.PAGESIZE);
		QueryWrapper<RecommendProductEntity> wrapper = new QueryWrapper<>();
		Page<RecommendProductEntity> RecommendProductPage = this.baseMapper.selectPage(page, wrapper);
		return RecommendProductPage;
    }

	@Override
	public void removeBatchByProductIds(String[] ids) {
		this.remove(new QueryWrapper<RecommendProductEntity>().in("product_id",ids));
	}

	@Override
	public List<String> getRecommendProductIds(Long size) {
		List<String> list;
		if (size==null||new Long(0L).equals(size)){
			list=this.list().stream().map(item->{
				return item.getProductId();
			}).collect(Collectors.toList());
			return list;
		}
		list=this.list(new QueryWrapper<RecommendProductEntity>().last("limit "+ size+" order by rand()")).stream().map(item->{
			return item.getProductId();
		}).collect(Collectors.toList());
		return list;
	}

	@Override
	public Boolean removeRecommend(String productId) {
		ProductInfoEntity productInfoEntity = new ProductInfoEntity();
		productInfoEntity.setIsRecommend(0);
		productInfoEntity.setId(productId);
		return productInfoService.updateById(productInfoEntity);
	}

	@Override
	public Boolean setRecommend(String productId) {
		ProductInfoEntity productInfoEntity = new ProductInfoEntity();
		productInfoEntity.setIsRecommend(1);
		productInfoEntity.setId(productId);
		return productInfoService.updateById(productInfoEntity);
	}

}
