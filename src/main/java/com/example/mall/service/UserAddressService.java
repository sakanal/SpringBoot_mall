package com.example.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mall.entity.UserAddressEntity;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * 
 *
 * @author ouyang
 * @email wanbzoy@163.com
 */
public interface UserAddressService extends IService<UserAddressEntity> {

	Page<UserAddressEntity> getPage(Map<String, Object> params);
}

