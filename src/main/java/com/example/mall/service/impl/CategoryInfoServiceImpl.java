package com.example.mall.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.SelectArg;
import org.springframework.stereotype.Service;

import java.util.*;
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
			return categoryEntity.getParentCid().equals(0L);
		}).map(menu->{
			menu.setChildren(getChildrens(menu,list));
			return menu;
		}).sorted((m1,m2)->{
			return (m1.getSort()==null?0:m1.getSort())-(m2.getSort()==null?0:m2.getSort());
		}).collect(Collectors.toList());
		return level1;
	}

	@Override
	public List<CategoryInfoEntity> getOneAndTwoLevel() {
		QueryWrapper<CategoryInfoEntity> oneQueryWrapper = new QueryWrapper<CategoryInfoEntity>().eq("cat_level", 1).orderByDesc("sort");
		QueryWrapper<CategoryInfoEntity> twoQueryWrapper = new QueryWrapper<CategoryInfoEntity>().eq("cat_level", 2).orderByDesc("sort");
		//获取所有一级分类
		List<CategoryInfoEntity> oneList = this.list(oneQueryWrapper);
		//获取所有二级分类
		List<CategoryInfoEntity> twoList = this.list(twoQueryWrapper);

		List<CategoryInfoEntity> resultList = new ArrayList<>();
		for (CategoryInfoEntity oneEntity : oneList){
			//创建子类别列表
			ArrayList<CategoryInfoEntity> childrenList = new ArrayList<>();
			Long catId = oneEntity.getCatId();
			for (CategoryInfoEntity twoEntity : twoList){
				Long parentCid = twoEntity.getParentCid();
				if (Objects.equals(catId, parentCid)){
					//将符合要求的子类别加入列表中
					childrenList.add(twoEntity);
				}
			}
			//该一级分类存在二级分类，则将二级分类列表放入一级分类中
			if (childrenList.size()>0){
				oneEntity.setChildren(childrenList);
			}
			//将最终的一级分类放入返回结果列表中
			resultList.add(oneEntity);
		}
		return resultList.size()>0?resultList:null;
	}

	@Override
	public CategoryInfoEntity getThreeLevel(Long catId) {
		CategoryInfoEntity parentEntity = this.getById(catId);
		if (parentEntity!=null){
			List<CategoryInfoEntity> childrenList = this.list(new QueryWrapper<CategoryInfoEntity>().eq("parent_cid", catId).orderByDesc("sort"));
			if (childrenList.size()>0){
				parentEntity.setChildren(childrenList);
			}
			return parentEntity;
		}else {
			return null;
		}
	}

	/**
	 * 根据分类id获取分类路径
	 * @param id
	 * @return
	 */
	@Override
	public List<Long> getCategoryPath(Long id) {
		Long parentId=-1L;
		CategoryInfoEntity categoryInfoEntity = this.getById(id);
		ArrayList<Long> res = new ArrayList<>();
		res.add(id);
		while (!new Long(0L).equals(categoryInfoEntity.getParentCid())){
			categoryInfoEntity=this.getById(categoryInfoEntity.getParentCid());
			res.add(categoryInfoEntity.getCatId());
		}
		Collections.reverse(res);
		return res;
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
