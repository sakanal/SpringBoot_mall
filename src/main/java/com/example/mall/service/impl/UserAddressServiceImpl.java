package com.example.mall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.SelectArg;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.mall.dao.UserAddressDao;
import com.example.mall.entity.UserAddressEntity;
import com.example.mall.service.UserAddressService;


@Service("userAddressService")
public class UserAddressServiceImpl extends ServiceImpl<UserAddressDao, UserAddressEntity> implements UserAddressService {

    @Override
    public Page<UserAddressEntity> getPage(Map<String, Object> params) {
		Integer current = params.get("current") == null ? 1 : new Integer(params.get("current").toString());
		Page<UserAddressEntity> page=new Page<UserAddressEntity>(current, SelectArg.PAGESIZE);
		QueryWrapper<UserAddressEntity> wrapper = new QueryWrapper<>();
		Page<UserAddressEntity> UserAddressPage = this.baseMapper.selectPage(page, wrapper);
		return UserAddressPage;
    }

}