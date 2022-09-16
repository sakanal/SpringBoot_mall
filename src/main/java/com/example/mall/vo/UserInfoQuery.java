package com.example.mall.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel("用户搜索条件")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoQuery {
    private String userName;
    private Integer role;
    private String startTime;
    private String endTime;
}
