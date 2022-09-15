package com.example.mall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.SelectArg;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.mall.dao.RecommendProductDao;
import com.example.mall.entity.RecommendProductEntity;
import com.example.mall.service.RecommendProductService;


@Service("recommendProductService")
public class RecommendProductServiceImpl extends ServiceImpl<RecommendProductDao, RecommendProductEntity> implements RecommendProductService {

    @Override
    public Page<RecommendProductEntity> getPage(Map<String, Object> params) {
		Integer current = params.get("current") == null ? 1 : new Integer(params.get("current").toString());
		Page<RecommendProductEntity> page=new Page<RecommendProductEntity>(current, SelectArg.PAGESIZE);
		QueryWrapper<RecommendProductEntity> wrapper = new QueryWrapper<>();
		Page<RecommendProductEntity> RecommendProductPage = this.baseMapper.selectPage(page, wrapper);
		return RecommendProductPage;
    }

}