package com.example.mall.controller;

import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.mall.entity.RecommendProductEntity;
import com.example.mall.service.RecommendProductService;
import com.example.mall.vo.R;



/**
 * 
 *
 * @author ouyang
 * @email wanbzoy@163.com
 */
@RestController
@RequestMapping("/recommendproduct")
public class RecommendProductController {
    @Autowired
    private RecommendProductService recommendProductService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        Page<RecommendProductEntity> page = recommendProductService.getPage(params);

        return R.ok().setData(page);
    }


    /**
     * 根据id查询信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
		RecommendProductEntity recommendProduct = recommendProductService.getById(id);

        return R.ok().setData(recommendProduct);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody RecommendProductEntity recommendProduct){
		recommendProductService.save(recommendProduct);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody RecommendProductEntity recommendProduct){
		recommendProductService.updateById(recommendProduct);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody String[] ids){
		recommendProductService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
