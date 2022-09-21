package com.example.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.OrderState;
import com.example.mall.constant.SelectArg;
import com.example.mall.entity.CartInfoEntity;
import com.example.mall.entity.OrderProductEntity;
import com.example.mall.entity.ProductInfoEntity;
import com.example.mall.service.CartInfoService;
import com.example.mall.service.OrderProductService;
import com.example.mall.service.ProductInfoService;
import com.example.mall.vo.OrderInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.mall.dao.OrderInfoDao;
import com.example.mall.entity.OrderInfoEntity;
import com.example.mall.service.OrderInfoService;
import org.springframework.transaction.annotation.Transactional;


@Service("orderInfoService")
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoDao, OrderInfoEntity> implements OrderInfoService {

	@Autowired
	private OrderProductService orderProductService;

	@Autowired
	private ProductInfoService productInfoService;

	@Autowired
	private CartInfoService cartInfoService;

    @Override
    public Page<OrderInfoEntity> getPage(Map<String, Object> params) {
		Integer current = params.get("current") == null ? 1 : new Integer(params.get("current").toString());
		Page<OrderInfoEntity> page=new Page<OrderInfoEntity>(current, SelectArg.PAGESIZE);
		QueryWrapper<OrderInfoEntity> wrapper = new QueryWrapper<>();
		Page<OrderInfoEntity> OrderInfoPage = this.baseMapper.selectPage(page, wrapper);
		return OrderInfoPage;
    }

	/**
	 *
	 * @param orderId
	 * @return
	 */
	@Override
	public OrderInfoEntity getByOrderId(String orderId) {
		//根据订单号查询订单信息
		OrderInfoEntity orderInfoEntity = this.getOne(new QueryWrapper<OrderInfoEntity>()
				.eq(!StrUtil.isBlank(orderId), "order_sn", orderId));
		//查询订单中所有涉及到的商品信息
		List<OrderProductEntity> orderProdectList = orderProductService.list(new QueryWrapper<OrderProductEntity>()
				.eq(!StrUtil.isBlank(orderInfoEntity.getOrderSn()), "order_sn", orderInfoEntity.getOrderSn()));
		List<String> productIds = orderProdectList.stream().map(item -> {
			return item.getProductId();
		}).collect(Collectors.toList());
		//设置商品数量
		List<ProductInfoEntity> productInfoEntities = productInfoService.listByIds(productIds);
		for (ProductInfoEntity productInfoEntity : productInfoEntities) {
			productInfoEntity.setNumber(getOrderProductNumber(productInfoEntity.getId(),orderProdectList));
		}
		//封装商品信息到订单
		orderInfoEntity.setProductList(productInfoEntities);
		return orderInfoEntity;
	}

	@Override
	public List<OrderInfoEntity> getProductsByUserId(String id) {
		return this.list(new QueryWrapper<OrderInfoEntity>().eq(StrUtil.isBlank(id),"user_id",id));
	}

	@Transactional
	@Override
	public void saveOrder(List<OrderInfoVo> orderInfoList) {
		//生成订单号
		String uuid = IdWorker.get32UUID();
		OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
		//获取用户id
		String userId = orderInfoList.get(0).getUserId();
		orderInfoEntity.setUserId(userId);
		Double total = new Double(0d);
		orderInfoEntity.setOrderSn(uuid);
		for (OrderInfoVo orderInfoVo : orderInfoList) {
			//对订单中商品关系一个一个依次进行保存进订单商品
			OrderProductEntity orderProductEntity = new OrderProductEntity();
			orderProductEntity.setOrderSn(uuid);
			orderProductEntity.setNumber(orderInfoVo.getProductNumber());
			orderProductEntity.setProductId(orderInfoVo.getProductId());
			ProductInfoEntity productInfo = productInfoService.getById(orderInfoVo.getProductId());
			if (productInfo!=null){
				//计算商品总价
				total+=productInfo.getPrice()*orderInfoVo.getProductNumber();
			}
			orderProductService.save(orderProductEntity);
			CartInfoEntity cartInfoEntity = new CartInfoEntity();
			cartInfoEntity.setUserId(userId);
			cartInfoEntity.setProductId(orderInfoVo.getProductId());
			cartInfoService.removeByProductIds(cartInfoEntity);
		}
		//设置收获地址信息
		orderInfoEntity.setAddressId(orderInfoList.get(0).getAddressId());
		orderInfoEntity.setCreateTime(new Date());
		//随机生成运费
		Double freightAmount= new Random().nextDouble()*100;
		orderInfoEntity.setFreightAmount(freightAmount);
		orderInfoEntity.setTotalAmount(total);
		orderInfoEntity.setPayAmount(total+freightAmount);
		//设置订单状态为新建
		orderInfoEntity.setStatus(OrderState.NEW.getCode());
		//订单保存到数据库
		this.save(orderInfoEntity);
	}

	private Integer getOrderProductNumber(String id, List<OrderProductEntity> orderProdectList) {
		for (OrderProductEntity orderProductEntity : orderProdectList) {
			if (orderProductEntity.getId().equals(id)){
				return orderProductEntity.getNumber();
			}
		}
		return 0;
	}
}