package com.example.mall.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.DefaultPicture;
import com.example.mall.constant.SelectArg;
import com.example.mall.vo.PictureVo;
import com.example.mall.entity.ProductInfoEntity;
import com.example.mall.service.CartInfoService;
import com.example.mall.service.OrderProductService;
import com.example.mall.service.ProductInfoService;
import com.example.mall.service.RecommendProductService;
import com.example.mall.vo.ProductQuery;
import com.example.mall.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;


/**
 * @author ouyang
 * @email wanbzoy@163.com
 */
@Api(tags = "商品信息管理")
@Slf4j
@RestController
@RequestMapping("/productInfo")
public class ProductInfoController {
	@Autowired
	private ProductInfoService productInfoService;
	@Autowired
	private RecommendProductService recommendProductService;
	@Autowired
	private OrderProductService orderProductService;
	@Autowired
	private CartInfoService cartInfoService;

	/**
	 * 随机获取商品
	 * @param size
	 * @return
	 */
	@ApiOperation("随机获取商品")
	@PostMapping("/getRandomProduct/{size}")
//	@Cacheable(value = "Product",key = "'randomProduct'")
	public R pageFind(@PathVariable("size") Long size) {
		List<ProductInfoEntity> list = productInfoService.getRandomProduct(size);
		if (list.size()>0){
			return R.ok().setData(list);
		}else {
			return R.error();
		}
	}

	@ApiOperation("根据商家id查询所有商品")
	@PostMapping("/listProduct/{id}")
	public R pageFind(@PathVariable("id") String id) {
		List<ProductInfoEntity> list = productInfoService.getProductsByUserId(id);

		return R.ok().setData(list);
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		Page<ProductInfoEntity> page = productInfoService.getPage(params);

		return R.ok().setData(page);
	}

	@ApiOperation("条件查询，分页")
	@PostMapping("/pageFind/{current}")
	public R pageFind(@PathVariable("current") Integer current,
					  @RequestBody(required = false) ProductQuery productQuery) {
		Page<ProductInfoEntity> page = productInfoService.getPage(current, productQuery);
		if (page.getRecords().size() > 0) {
			return R.ok().setData(page);
		} else {
			return R.error().setMessage("暂无数据");
		}
	}


	/**
	 * 根据id查询信息
	 */
	@GetMapping("/info/{id}")
	public R info(@PathVariable("id") String id) {
		ProductInfoEntity productInfo = productInfoService.getById(id);
		if (productInfo!=null){
			return R.ok().setData(productInfo);
		}else {
			return R.error();
		}
	}

	@ApiOperation("根据商品类别id，获取商品信息列表")
	@GetMapping("/getByCategoryId/{current}/{catId}")
	public R getByCategoryId(@PathVariable("current") Integer current,
							 @PathVariable("catId") Long catId) {
		Page<ProductInfoEntity> listPage = new Page<>(current, SelectArg.PAGESIZE);
		productInfoService.page(listPage, new QueryWrapper<ProductInfoEntity>().eq("cat_id", catId));
		if (listPage.getRecords().size() > 0) {
			return R.ok().setData(listPage);
		} else {
			return R.error().setMessage("暂无数据");
		}
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public R save(@RequestBody ProductInfoEntity productInfo) {
		List<PictureVo> pictureList = productInfo.getPictureList();
		//对图片url进行转换
		if (pictureList.size() > 0) {
			String str = JSONUtil.toJsonStr(pictureList);
			productInfo.setPicture(str);
		}else {
			productInfo.setPicture(DefaultPicture.DEFAULT_URL);
		}
		// 将图片数据保存到数据库中
		productInfoService.save(productInfo);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@PutMapping("/update")
	public R update(@RequestBody ProductInfoEntity productInfo) {
		List<PictureVo> pictureList = productInfo.getPictureList();
		//对图片url进行转换
		if (pictureList.size() > 0) {
			String str = JSONUtil.toJsonStr(pictureList);
			productInfo.setPicture(str);
		}
		log.info(String.valueOf(pictureList));
		productInfoService.updateById(productInfo);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@DeleteMapping("/delete")
	public R delete(@RequestBody String[] ids) {
		//删除商品关联信息
		productInfoService.removeByIds(Arrays.asList(ids));
		orderProductService.removeByProductIds(ids);
		//删除商品信息
		recommendProductService.removeBatchByProductIds(ids);

		return R.ok();
	}

}
