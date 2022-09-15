package com.example.mall.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.SelectArg;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.mall.dao.CategoryInfoDao;
import com.example.mall.entity.CategoryInfoEntity;
import com.example.mall.service.CategoryInfoService;


@Service("categoryInfoService")
public class CategoryInfoServiceImpl extends ServiceImpl<CategoryInfoDao, CategoryInfoEntity> implements CategoryInfoService {

    @Override
    public Page<CategoryInfoEntity> getPage(Map<String, Object> params) {
		Integer current = params.get("current") == null ? 1 : new Integer(params.get("current").toString());
		Page<CategoryInfoEntity> page=new Page<CategoryInfoEntity>(current, SelectArg.PAGESIZE);
		QueryWrapper<CategoryInfoEntity> wrapper = new QueryWrapper<>();
		Page<CategoryInfoEntity> CategoryInfoPage = this.baseMapper.selectPage(page, wrapper);
		return CategoryInfoPage;
    }

	@Override
	public List<CategoryInfoEntity> listWithTree() {
		List<CategoryInfoEntity> list = baseMapper.selectList(null);
		// 过滤一级分类
		List<CategoryInfoEntity> level1 = list.stream().filter(categoryEntity -> {
			return categoryEntity.getParentCid().equals(0);
		}).map(menu->{
			menu.setChildren(getChildrens(menu,list));
			return menu;
		}).sorted((m1,m2)->{
			return (m1.getSort()==null?0:m1.getSort())-(m2.getSort()==null?0:m2.getSort());
		}).collect(Collectors.toList());
		return list;
	}

	/**
	 * 查出所有菜单的子菜单
	 * @param root
	 * @param entities
	 * @return
	 */
	private List<CategoryInfoEntity> getChildrens(CategoryInfoEntity root,List<CategoryInfoEntity> entities){
		Long parentCid = root.getCatId();
		List<CategoryInfoEntity> list = entities.stream().filter(entity -> {
			return entity.getParentCid().equals(parentCid);
		}).map(categoryEntity -> {
			categoryEntity.setChildren(getChildrens(categoryEntity,entities));
			return categoryEntity;
		}).sorted((m1,m2)->{
			return (m1.getSort()==null?0:m1.getSort())-(m2.getSort()==null?0:m2.getSort());
		}).collect(Collectors.toList());
		return list;
	}

}