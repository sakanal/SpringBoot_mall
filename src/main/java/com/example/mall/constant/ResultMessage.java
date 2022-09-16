package com.example.mall.constant;

public enum ResultMessage {
    SUCCESS("成功",20000),
    NO_RESULT_DATA("暂无数据",20001),
    NOT_FOUND("未找到路径",40004),
    MISSING_PARAMETERS("缺少参数",40001),
    NO_PARAMETERS("无参数",40002),
    ADD_USER_ERROR("添加用户失败，检查用户名与邮箱是否已经注册！",40003),
    LOGIN_ERROR("用户名或密码错误",40004),
    ERROR("失败",44444);

    private final String message;
    private final Integer code;
    ResultMessage(String message,Integer code) {
        this.message=message;
        this.code=code;
    }
    public String getMessage(){
        return this.message;
    }
    public Integer getCode(){
        return this.code;
    }
}
