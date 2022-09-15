package com.example.mall.controller;

import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.mall.entity.OrderProductEntity;
import com.example.mall.service.OrderProductService;
import com.example.mall.vo.R;



/**
 * 
 *
 * @author ouyang
 * @email wanbzoy@163.com
 */
@RestController
@RequestMapping("/orderproduct")
public class OrderProductController {
    @Autowired
    private OrderProductService orderProductService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        Page<OrderProductEntity> page = orderProductService.getPage(params);

        return R.ok().setData(page);
    }


    /**
     * 根据id查询信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
		OrderProductEntity orderProduct = orderProductService.getById(id);

        return R.ok().setData(orderProduct);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody OrderProductEntity orderProduct){
		orderProductService.save(orderProduct);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody OrderProductEntity orderProduct){
		orderProductService.updateById(orderProduct);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody String[] ids){
		orderProductService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}