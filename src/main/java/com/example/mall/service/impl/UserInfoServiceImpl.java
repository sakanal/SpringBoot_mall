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
		// 创建查询条件，统计相同用户名和邮箱的用户数
		QueryWrapper<UserInfoEntity> wrapper = new QueryWrapper<>();
		wrapper.eq(user.getUserName()!=null,"username",user.getUserName())
				.or().eq(user.getEmail()!=null,"email",user.getEmail());
		// 用户数大于0，代表有重复用户，无法创建新用户
		if (this.count(wrapper)>0) {
			return false;
		}
		UserInfoEntity userInfoEntity = new UserInfoEntity();
		BeanUtils.copyProperties(user,userInfoEntity);
		boolean save = false;
		try {
			// 保存用户信息
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
			// 判断输入的邮箱数据是否有值
			if (!StringUtils.hasText(email)){
				throw new MyException(20001,"登录邮箱为空");
			}
			// 判断输入的密码数据是否有值
			if (!StringUtils.hasText(password)){
				throw new MyException(20001,"登录密码为空");
			}
			// 设置邮箱为查询条件，查询数据库数据中是否存在用户
			QueryWrapper<UserInfoEntity> queryWrapper = new QueryWrapper<UserInfoEntity>().eq("email", email);
			UserInfoEntity userInfo = this.getOne(queryWrapper);
			// 查询结果为null，数据库中不存在对应的用户
			if (userInfo==null){
				throw new MyException(20001,"用户不存在");
			}
			// 判断查询出来的用户数据的密码和前端传递过来的密码是否相同
			if (Objects.equals(userInfo.getPassword(), password)){
				// 用户被逻辑删除了
				if (userInfo.getIsDelete() != 0){
					throw new MyException(20001,"用户无效");
				}else {
					// 登录成功，将用户id和用户名作为token的主体部分生成token，并返回生成的token
					return JwtUtil.getUserJwtToken(userInfo.getId(),userInfo.getUserName());
				}
			}else {
				// 前端传递的密码和数据库中的密码不匹配
				throw new MyException(20001,"密码错误");
			}
		}else {
			// 前端传递的数据体为空
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
