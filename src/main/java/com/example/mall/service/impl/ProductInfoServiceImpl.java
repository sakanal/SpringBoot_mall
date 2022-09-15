package com.example.mall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.SelectArg;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.mall.dao.ProductInfoDao;
import com.example.mall.entity.ProductInfoEntity;
import com.example.mall.service.ProductInfoService;


@Service("productInfoService")
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoDao, ProductInfoEntity> implements ProductInfoService {

    @Override
    public Page<ProductInfoEntity> getPage(Map<String, Object> params) {
		Integer current = params.get("current") == null ? 1 : new Integer(params.get("current").toString());
		Page<ProductInfoEntity> page=new Page<ProductInfoEntity>(current, SelectArg.PAGESIZE);
		QueryWrapper<ProductInfoEntity> wrapper = new QueryWrapper<>();
		Page<ProductInfoEntity> ProductInfoPage = this.baseMapper.selectPage(page, wrapper);
		return ProductInfoPage;
    }

}