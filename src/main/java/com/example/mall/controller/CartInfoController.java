package com.example.mall.controller;

import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.mall.entity.CartInfoEntity;
import com.example.mall.service.CartInfoService;
import com.example.mall.vo.R;



/**
 *
 *
 * @author ouyang
 * @email wanbzoy@163.com
 */
@Api(tags = "购物车管理")
@RestController
@RequestMapping("/cartinfo")
public class CartInfoController {
    @Autowired
    private CartInfoService cartInfoService;

    /**
     * 列表
     */
    @ApiOperation("分页查询")
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        Page<CartInfoEntity> page = cartInfoService.getPage(params);

        return R.ok().setData(page);
    }


    /**
     * 根据id查询信息
     */
    @ApiOperation("根据id查询信息")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
		CartInfoEntity cartInfo = cartInfoService.getById(id);

        return R.ok().setData(cartInfo);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    public R save(@RequestBody CartInfoEntity cartInfo){
		cartInfoService.save(cartInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("根据id修改")
    @PutMapping("/update")
    public R update(@RequestBody CartInfoEntity cartInfo){
		cartInfoService.updateById(cartInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("根据id删除")
    @DeleteMapping("/delete")
    public R delete(@RequestBody String[] ids){
		cartInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
