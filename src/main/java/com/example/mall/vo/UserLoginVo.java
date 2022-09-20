package com.example.mall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("用户登录VO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVo {
    private String email;
    private String password;
}
