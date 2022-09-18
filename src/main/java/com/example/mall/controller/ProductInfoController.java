package com.example.mall.controller;

import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.vo.ProductQuery;
import com.example.mall.service.RecommendProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.omg.CORBA.Current;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.mall.entity.ProductInfoEntity;
import com.example.mall.service.ProductInfoService;
import com.example.mall.vo.R;



/**
 *
 *
 * @author ouyang
 * @email wanbzoy@163.com
 */
@Api(tags = "商品信息管理")
@RestController
@RequestMapping("/productInfo")
public class ProductInfoController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private RecommendProductService recommendProductService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        Page<ProductInfoEntity> page = productInfoService.getPage(params);

        return R.ok().setData(page);
    }

    @ApiOperation("条件查询，分页")
    @GetMapping("/pageFind/{current}")
    public R pageFind(@PathVariable("current")Integer current,
                      @RequestBody(required = false) ProductQuery productQuery){
        Page<ProductInfoEntity> page = productInfoService.getPage(current,productQuery);
        if (page.getRecords().size()>0){
            return R.ok().setData(page);
        }else {
            return R.error().setMessage("暂无数据");
        }
    }


    /**
     * 根据id查询信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
		ProductInfoEntity productInfo = productInfoService.getById(id);

        return R.ok().setData(productInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ProductInfoEntity productInfo){
		productInfoService.save(productInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody ProductInfoEntity productInfo){
		productInfoService.updateById(productInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody String[] ids){
		productInfoService.removeByIds(Arrays.asList(ids));
        recommendProductService.removeBatchByProductIds(ids);

        return R.ok();
    }

}
