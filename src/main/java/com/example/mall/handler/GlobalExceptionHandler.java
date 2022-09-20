package com.example.mall.handler;

import com.example.mall.exception.MyException;
import com.example.mall.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        log.info("Exception异常捕获");
        e.printStackTrace();
        return R.error().setMessage("执行了全局统一异常处理");
    }
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        log.info("ArithmeticException异常捕获");
        e.printStackTrace();
        return R.error().setMessage("ArithmeticException异常捕获");
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public R error(MethodArgumentTypeMismatchException e){
        log.info("MethodArgumentTypeMismatchException异常捕获");
        e.printStackTrace();
        return R.error().setMessage("控制器获取形参失败，类型错误");
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseBody
    public R error(SQLIntegrityConstraintViolationException e){
        log.info("SQLIntegrityConstraintViolationException异常捕获");
        e.printStackTrace();
        return R.error().setMessage("数据重复，数据库数据未删除，请联系管理员");
    }
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public R error(MyException e){
        log.info("MyException异常捕获");
        e.printStackTrace();
        return R.error().setCode(e.getExceptionCode()).setMessage(e.getExceptionMessage());
    }
}
