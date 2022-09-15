package com.example.mall.vo;

import com.example.mall.constant.ResultMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class R {
    private Integer code;
    private Boolean success;
    private String message;
    private Object data;

    public R(String message,Integer code) {
        this.code = code;
        this.message = message;
    }
    public R(Integer code, Boolean success, String message) {
        this.code = code;
        this.success = success;
        this.message = message;
    }

    public static R ok(){
        R r = new R();
        r.code = ResultMessage.SUCCESS.getCode();
        r.message = ResultMessage.SUCCESS.getMessage();
        return r;
    }
    public static R error(){
        R r = new R();
        r.code=ResultMessage.ERROR.getCode();
        r.message=ResultMessage.ERROR.getMessage();
        return r;
    }
}
