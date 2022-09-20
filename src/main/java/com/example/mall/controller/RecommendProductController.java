package com.example.mall.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.entity.ProductInfoEntity;
import com.example.mall.service.ProductInfoService;
import com.example.mall.vo.PictureVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import com.example.mall.entity.RecommendProductEntity;
import com.example.mall.service.RecommendProductService;
import com.example.mall.vo.R;

import javax.annotation.Resource;


/**
 *
 *
 * @author ouyang
 * @email wanbzoy@163.com
 */
@Api(tags = "商品推荐管理")
@RestController
@RequestMapping("/recommendProduct")
public class RecommendProductController {
    @Autowired
    private RecommendProductService recommendProductService;
    @Resource
    private ProductInfoService productInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        Page<RecommendProductEntity> page = recommendProductService.getPage(params);

        return R.ok().setData(page);
    }

    /**
     * 获取size个推荐商品id  size为0或空返回所有
     * @param size
     * @return
     */
    @GetMapping("/getRecommend/{size}")
    @Cacheable(value = "Product",key = "randomRecommendProduct")
    public R list(@PathVariable("size")Long size){
//        List<String> recommendProductIds = recommendProductService.getRecommendProductIds(size);
        List<ProductInfoEntity> productInfoEntityList = productInfoService.randomGetRecommendProduct(size);
//        for (ProductInfoEntity productInfo : productInfoEntityList) {
//            String picture = productInfo.getPicture();
//            JSONArray jsonArray = JSONUtil.parseArray(picture);
//            List<PictureVo> pictureVos = JSONUtil.toList(jsonArray, PictureVo.class);
//            productInfo.setPictureList(pictureVos);
//        }
        return R.ok().setData(productInfoEntityList);
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

    @ApiOperation("根据商品id设置推荐商品")
    @PutMapping("/setRecommend/{productId}")
    public R setRecommend(@PathVariable("productId")String productId){
        Boolean result = recommendProductService.setRecommend(productId);
        return result?R.ok():R.error();
    }
    @ApiOperation("根据商品id取消推荐商品")
    @PutMapping("/removeRecommend/{productId}")
    public R removeRecommend(@PathVariable("productId")String productId){
        Boolean result = recommendProductService.removeRecommend(productId);
        return result?R.ok():R.error();
    }

}
