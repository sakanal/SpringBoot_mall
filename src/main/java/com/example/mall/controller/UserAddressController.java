package com.example.mall.controller;

import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.mall.entity.UserAddressEntity;
import com.example.mall.service.UserAddressService;
import com.example.mall.vo.R;



/**
 *
 *
 * @author ouyang
 * @email wanbzoy@163.com
 */
@Api(tags = "用户地址管理")
@RestController
@RequestMapping("/userAddress")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        Page<UserAddressEntity> page = userAddressService.getPage(params);

        return R.ok().setData(page);
    }


    /**
     * 根据id查询信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") String id){
		UserAddressEntity userAddress = userAddressService.getById(id);

        return R.ok().setData(userAddress);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody UserAddressEntity userAddress){
		userAddressService.save(userAddress);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody UserAddressEntity userAddress){
		userAddressService.updateById(userAddress);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody String[] ids){
		userAddressService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
