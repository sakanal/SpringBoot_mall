package com.example.mall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("商品搜索条件")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuery {

    @ApiModelProperty("商品类别id")
    private Long catId;
    @ApiModelProperty("商品名称")
    private String name;
    @ApiModelProperty("商家id")
    private String userId;
    @ApiModelProperty("商品价格")
    private Integer price;
}
