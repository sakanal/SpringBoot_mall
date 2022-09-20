package com.example.mall.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @GetMapping("/Alist")
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
    @ApiOperation("根据用户id获取该用户的所有收货地址")
    @GetMapping("/list/{userId}")
    public R getAddressListByUserId(@PathVariable("userId")String userId){
        List<UserAddressEntity> list = userAddressService.list(new QueryWrapper<UserAddressEntity>().eq("user_id", userId));
        if (list.size()>0){
            return R.ok().setData(list);
        }else {
            return R.error().setMessage("暂无收货地址");
        }
    }

    /**
     * 保存
     */
    @ApiOperation("添加用户的收货地址")
    @PostMapping("/save")
    public R save(@RequestBody UserAddressEntity userAddress){
		userAddressService.save(userAddress);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改用户的收货地址")
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
