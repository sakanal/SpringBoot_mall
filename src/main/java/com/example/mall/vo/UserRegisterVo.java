package com.example.mall.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterVo {
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @TableField(value = "username")
    private String userName;
    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;
    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    private String code;
}
