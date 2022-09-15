package com.example.mall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.SelectArg;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.mall.dao.OrderInfoDao;
import com.example.mall.entity.OrderInfoEntity;
import com.example.mall.service.OrderInfoService;


@Service("orderInfoService")
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoDao, OrderInfoEntity> implements OrderInfoService {

    @Override
    public Page<OrderInfoEntity> getPage(Map<String, Object> params) {
		Integer current = params.get("current") == null ? 1 : new Integer(params.get("current").toString());
		Page<OrderInfoEntity> page=new Page<OrderInfoEntity>(current, SelectArg.PAGESIZE);
		QueryWrapper<OrderInfoEntity> wrapper = new QueryWrapper<>();
		Page<OrderInfoEntity> OrderInfoPage = this.baseMapper.selectPage(page, wrapper);
		return OrderInfoPage;
    }

}