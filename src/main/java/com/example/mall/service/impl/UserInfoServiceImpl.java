package com.example.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.SelectArg;
import com.example.mall.exception.MyException;
import com.example.mall.utils.JwtUtil;
import com.example.mall.vo.UserInfoQuery;
import com.example.mall.vo.UserLoginVo;
import com.example.mall.vo.UserPasswordVo;
import com.example.mall.vo.UserRegisterVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.mall.dao.UserInfoDao;
import com.example.mall.entity.UserInfoEntity;
import com.example.mall.service.UserInfoService;
import org.springframework.util.StringUtils;


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
	public Page<UserInfoEntity> getPage(Integer current, UserInfoQuery userInfoQuery) {
		Page<UserInfoEntity> userInfoEntityPage = new Page<>(current, SelectArg.PAGESIZE);
		QueryWrapper<UserInfoEntity> userInfoEntityQueryWrapper = new QueryWrapper<>();
		userInfoEntityQueryWrapper.orderByDesc("update_time");
		if (userInfoQuery != null){
			String userName = userInfoQuery.getUserName();
			Integer role = userInfoQuery.getRole();
			String startTime = userInfoQuery.getStartTime();
			String endTime = userInfoQuery.getEndTime();
			if(StringUtils.hasText(userName))
				userInfoEntityQueryWrapper.like("username",userName);
			if(!StringUtils.isEmpty(role))
				userInfoEntityQueryWrapper.eq("role",role);
			if(StringUtils.hasText(startTime))
				userInfoEntityQueryWrapper.ge("create_time",startTime);
			if(StringUtils.hasText(endTime))
				userInfoEntityQueryWrapper.le("create_time",endTime);
		}
		this.page(userInfoEntityPage,userInfoEntityQueryWrapper);

		if (userInfoEntityPage.getRecords().size()>0){
			return userInfoEntityPage;
		}else {
			return null;
		}
	}

	@Override
	public boolean addUser(UserRegisterVo user) {
		QueryWrapper<UserInfoEntity> wrapper = new QueryWrapper<>();
		wrapper.eq(user.getUserName()!=null,"username",user.getUserName())
				.or().eq(user.getEmail()!=null,"email",user.getEmail());
		if (this.count(wrapper)>0) {
			return false;
		}
		UserInfoEntity userInfoEntity = new UserInfoEntity();
		BeanUtils.copyProperties(user,userInfoEntity);
		boolean save = false;
		try {
			save = this.save(userInfoEntity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException(20001,"注册失败，请联系管理员");
		}
		return save;
	}

	@Override
	public String login(UserLoginVo userLoginVo) {
		if (userLoginVo!=null){
			String email = userLoginVo.getEmail();
			String password = userLoginVo.getPassword();
			if (!StringUtils.hasText(email)){
				throw new MyException(20001,"登录邮箱为空");
			}
			if (!StringUtils.hasText(password)){
				throw new MyException(20001,"登录密码为空");
			}
			QueryWrapper<UserInfoEntity> queryWrapper = new QueryWrapper<UserInfoEntity>().eq("email", email);
			UserInfoEntity userInfo = this.getOne(queryWrapper);
			if (userInfo==null){
				throw new MyException(20001,"用户不存在");
			}
			if (Objects.equals(userInfo.getPassword(), password)){
				if (userInfo.getIsDelete() != 0){
					throw new MyException(20001,"用户无效");
				}else {
					return JwtUtil.getUserJwtToken(userInfo.getId(),userInfo.getUserName());
				}
			}else {
				throw new MyException(20001,"密码错误");
			}

		}else {
			throw new MyException(20001,"登录信息为空");
		}
	}

	@Override
	public String updatePassword(UserPasswordVo userPasswordVo) {
		String userId = userPasswordVo.getUserId();
		String password = userPasswordVo.getPassword();
		String newPassword = userPasswordVo.getNewPassword();
		QueryWrapper<UserInfoEntity> checkQueryWrapper = new QueryWrapper<UserInfoEntity>().eq("id", userId).eq("password", password);
		long count = this.count(checkQueryWrapper);
		if (count == 1){
			UserInfoEntity userInfoEntity = new UserInfoEntity();
			userInfoEntity.setId(userId);
			userInfoEntity.setPassword(newPassword);
			boolean update = this.updateById(userInfoEntity);
			if (update){
				UserInfoEntity userInfo = this.getById(userId);
				return JwtUtil.getUserJwtToken(userInfo.getId(), userInfo.getUserName());
			}else {
				throw new MyException(20001,"修改失败");
			}
		}else {
			throw new MyException(20001,"密码错误，无法修改");
		}
	}

}
