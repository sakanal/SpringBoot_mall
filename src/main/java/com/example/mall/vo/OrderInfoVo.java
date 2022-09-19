package com.example.mall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("前端传递的订单数据")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoVo {

    @ApiModelProperty("购买人id")
    private String userId;
    @ApiModelProperty("商品id")
    private String productId;
    @ApiModelProperty("该商品的数量")
    private String productNumber;
    @ApiModelProperty("该商品的单价")
    private Integer unitPrice;
    @ApiModelProperty("该商品的总价")
    private Integer totalPrice;
}
