package com.example.mall.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.mall.entity.CategoryInfoEntity;
import com.example.mall.service.CategoryInfoService;
import com.example.mall.vo.R;


/**
 * 商品分类
 *
 * @author ouyang
 * @email wanbzoy@163.com
 */
@Api(tags = "商品分类管理")
@RestController
@RequestMapping("/categoryInfo")
public class CategoryInfoController {
	@Autowired
	private CategoryInfoService categoryInfoService;

	@ApiOperation("通过三级分类id返回所需要的各个级别数组")
	@GetMapping("/getPath/{id}")
	public R getTypePath(@PathVariable("id") Long id) {
		List<Long> path = categoryInfoService.getCategoryPath(id);

		return R.ok().setData(path);
	}

	/**
	 * 列表
	 */
	@ApiOperation("分页查询")
	@GetMapping("/list")
	public R list( @RequestParam Map<String, Object> params) {
		Page<CategoryInfoEntity> page = categoryInfoService.getPage(params);

		return R.ok().setData(page);
	}

	/**
	 * 查出所有分类及其子分类，以树形结构组装
	 */
	@ApiOperation("查出所有分类及其子分类，以树形结构组装")
	@GetMapping("/list/tree")
	public R get() {
		List<CategoryInfoEntity> list = categoryInfoService.listWithTree();

		return R.ok().setData(list);
	}

	@ApiOperation("获取一级和二级分类")
	@GetMapping("/getOneAndTwoLevel")
	public R getOneAndTwoLevel(){
		List<CategoryInfoEntity> categoryInfoEntityList = categoryInfoService.getOneAndTwoLevel();
		if (categoryInfoEntityList!=null){
			return R.ok().setData(categoryInfoEntityList);
		}else {
			return R.error(ResultMessage.NO_RESULT_DATA);
		}
	}

	@ApiOperation("根据父id获取类别信息以及其下一级的所有子类别")
	@GetMapping("/getThreeLevel/{catId}")
	public R getThreeLevel(@PathVariable("catId")Long catId){
		CategoryInfoEntity categoryInfoEntity = categoryInfoService.getThreeLevel(catId);
		if (categoryInfoEntity!=null){
			return R.ok().setData(categoryInfoEntity);
		}else {
			return R.error(ResultMessage.NO_RESULT_DATA);
		}
	}

	/**
	 * 根据id查询信息
	 */
	@ApiOperation("根据id查询信息")
	@GetMapping("/info/{catId}")
	public R info(@PathVariable("catId") Long catId) {
		CategoryInfoEntity categoryInfo = categoryInfoService.getById(catId);

		return R.ok().setData(categoryInfo);
	}

	/**
	 * 保存
	 */
	@ApiOperation("保存")
	@PostMapping("/save")
	public R save(@RequestBody CategoryInfoEntity categoryInfo) {
		categoryInfoService.save(categoryInfo);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@ApiOperation("修改")
	@PutMapping("/update")
	public R update(@RequestBody CategoryInfoEntity categoryInfo) {
		categoryInfoService.updateById(categoryInfo);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@ApiOperation("删除")
	@DeleteMapping("/delete")
	public R delete(@RequestBody Long[] catIds) {
		categoryInfoService.removeByIds(Arrays.asList(catIds));

		return R.ok();
	}

}
