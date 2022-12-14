package com.example.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.SelectArg;
import com.example.mall.entity.ProductInfoEntity;
import com.example.mall.exception.MyException;
import com.example.mall.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.mall.dao.CartInfoDao;
import com.example.mall.entity.CartInfoEntity;
import com.example.mall.service.CartInfoService;


@Service("cartInfoService")
public class CartInfoServiceImpl extends ServiceImpl<CartInfoDao, CartInfoEntity> implements CartInfoService {

	@Autowired
	private ProductInfoService productInfoService;

	@Override
	public Page<CartInfoEntity> getPage(Map<String, Object> params) {
		Integer current = params.get("current") == null ? 1 : new Integer(params.get("current").toString());
		String key = params.get("key").toString();
		Page<CartInfoEntity> page = new Page<CartInfoEntity>(current, SelectArg.PAGESIZE);
		QueryWrapper<CartInfoEntity> wrapper = new QueryWrapper<>();
		wrapper.like(key == null, "user_id", key).or().like(key != null, "product_id", key);
		Page<CartInfoEntity> CartInfoPage = this.baseMapper.selectPage(page, wrapper);
		return CartInfoPage;
	}

	@Override
	public void removeByProductIds(CartInfoEntity ids) {
		this.remove(new QueryWrapper<CartInfoEntity>().eq("user_id",ids.getUserId()).eq("product_id",ids.getProductId()));
	}

	@Override
	public List<ProductInfoEntity> getProductByUserId(String userId) {
		List<CartInfoEntity> cartInfoEntities = this.list(new QueryWrapper<CartInfoEntity>().eq("user_id", userId));
		List<String> productIds = cartInfoEntities.stream().map(item -> {
			return item.getProductId();
		}).collect(Collectors.toList());
		if (productIds==null||productIds.size()<1){
			return null;
		}
		List<ProductInfoEntity> productInfoEntities = productInfoService.listByIds(productIds).stream().map(item -> {
			item.setNumber(getProductNumber(item.getId(), cartInfoEntities));
			return item;
		}).collect(Collectors.toList());
		return productInfoEntities;
	}

	@Override
	public void updateByUserId(CartInfoEntity cartInfo) {
		String userId = cartInfo.getUserId();
		String productId = cartInfo.getProductId();
		Integer number = cartInfo.getNumber();
		QueryWrapper<CartInfoEntity> queryWrapper = new QueryWrapper<CartInfoEntity>()
				.eq("user_id", userId)
				.eq("product_id", productId)
				.select("number");
		CartInfoEntity cartInfoEntity = this.getOne(queryWrapper);
		if (cartInfoEntity.getNumber()>0){
			cartInfo.setNumber(cartInfoEntity.getNumber()+number);
		}
		this.update(cartInfo, new UpdateWrapper<CartInfoEntity>()
				.eq("user_id",userId)
				.eq("product_id",productId));
	}

	@Override
	public void updateInCartByUserId(CartInfoEntity cartInfo) {
		String userId = cartInfo.getUserId();
		String productId = cartInfo.getProductId();
		Integer number = cartInfo.getNumber();
		// ??????????????????????????????
		QueryWrapper<CartInfoEntity> queryWrapper = new QueryWrapper<CartInfoEntity>()
				.eq("user_id", userId)
				.eq("product_id", productId);
		CartInfoEntity cartInfoEntity = this.getOne(queryWrapper);
		// ????????????????????????
		if (cartInfoEntity!=null){
			// ???????????????????????????????????????????????????
			cartInfoEntity.setNumber(number);
			// ???????????????
			this.update(cartInfoEntity,queryWrapper);
		}else { // ???????????????????????????
			throw new MyException(20001,"????????????");
		}
	}

	private Integer getProductNumber(String id, List<CartInfoEntity> cartInfoEntities) {
		for (CartInfoEntity cartInfoEntity : cartInfoEntities) {
			if (id.equals(cartInfoEntity.getProductId())){
				return cartInfoEntity.getNumber();
			}
		}
		return 0;
	}

}
