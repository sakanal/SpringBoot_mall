package com.example.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.SelectArg;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.mall.dao.UserInfoDao;
import com.example.mall.entity.UserInfoEntity;
import com.example.mall.service.UserInfoService;


@Service("userInfoService")
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfoEntity> implements UserInfoService {

    @Override
    public Page<UserInfoEntity> getPage(Map<String, Object> params) {
		Integer current = params.get("current") == null ? 1 : new Integer(params.get("current").toString());
		Page<UserInfoEntity> page=new Page<UserInfoEntity>(current, SelectArg.PAGESIZE);
		QueryWrapper<UserInfoEntity> wrapper = new QueryWrapper<>();
		Page<UserInfoEntity> UserInfoPage = this.baseMapper.selectPage(page, wrapper);
		return UserInfoPage;
    }

	@Override
	public boolean addUser(UserInfoEntity user) {
		user.setCreateTime(new Date());
		QueryWrapper<UserInfoEntity> wrapper = new QueryWrapper<>();
		wrapper.eq(user.getUsername()!=null,"username",user.getUsername())
				.or().eq(user.getEmail()!=null,"email",user.getEmail())
				.or().eq(user.getMobile()!=null,"mobile",user.getMobile());
		if (this.count(wrapper)>0) {
			return false;
		}
		boolean success = this.save(user);
		return success;
	}

	@Override
	public boolean index(UserInfoEntity user) {
		if (StrUtil.isBlank(user.getPassword())||StrUtil.isBlank(user.getEmail())){
			return false;
		}
		long count = this.count(new QueryWrapper<UserInfoEntity>().eq("password", user.getPassword()).eq("email", user.getEmail()).last(" limit 1"));
		return count>0;
	}

}