package com.example.mall.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.constant.ResultMessage;
import com.example.mall.entity.OrderInfoEntity;
import com.example.mall.service.OrderInfoService;
import com.example.mall.service.OrderProductService;
import com.example.mall.vo.OrderInfoVo;
import com.example.mall.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



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

    @ApiOperation("根据用户id查询所有订单")
    @PostMapping("/listOrder/{id}")
    public R pageFind(@PathVariable("id") String id) {
        List<OrderInfoEntity> list = orderInfoService.getProductsByUserId(id);

        return R.ok().setData(list);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        Page<OrderInfoEntity> page = orderInfoService.getPage(params);

        return R.ok().setData(page);
    }

    @ApiOperation("条件搜索订单，分页")
    @PostMapping("/pageFind/{current}")
    public R pageFind(@PathVariable("current")Integer current,
                      @RequestBody(required = false)OrderInfoEntity orderInfoEntity){
        Page<OrderInfoEntity> page = orderInfoService.getPage(current,orderInfoEntity);
        if (page.getRecords().size()>0){
            return R.ok().setData(page);
        }else {
            return R.error().setMessage("暂无数据");
        }
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
    public R save(@RequestBody List<OrderInfoVo> orderInfoList){
        if (orderInfoList.size()<1){
            return R.error(ResultMessage.NO_PARAMETERS);
        }
		orderInfoService.saveOrder(orderInfoList);

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
    public R delete(@RequestBody String[] orderIds){
		orderInfoService.removeByOrderIds(orderIds);
        orderProductService.removeByOrderIds(orderIds);

        return R.ok();
    }

}
