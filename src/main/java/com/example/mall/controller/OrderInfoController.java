package com.example.mall.controller;

import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.service.OrderProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.mall.entity.OrderInfoEntity;
import com.example.mall.service.OrderInfoService;
import com.example.mall.vo.R;



/**
 *
 *
 * @author ouyang
 * @email wanbzoy@163.com
 */
@Api(tags = "订单信息管理")
@RestController
@RequestMapping("/orderInfo")
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private OrderProductService orderProductService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        Page<OrderInfoEntity> page = orderInfoService.getPage(params);

        return R.ok().setData(page);
    }


    /**
     * 根据订单id查询订单详细信息
     */
    @GetMapping("/info/{orderId}")
    public R info(@PathVariable("orderId") String orderId){
		OrderInfoEntity orderInfo = orderInfoService.getByOrderId(orderId);

        return R.ok().setData(orderInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody OrderInfoEntity orderInfo){
		orderInfoService.save(orderInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody OrderInfoEntity orderInfo){
		orderInfoService.updateById(orderInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody String[] ids){
		orderInfoService.removeByIds(Arrays.asList(ids));
        orderProductService.removeByOrderIds(ids);

        return R.ok();
    }

}