package com.example.mall.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class UserInfoAspect {

    @Around("execution(* com.example.mall.controller.*.page*(..))")
    public Object pageCurrentAspect(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("开始对页码进行判断");

        Object[] args = joinPoint.getArgs();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();

        for (int i = 0; i < args.length; i++) {
            if (parameterNames[i].contains("current")){
                log.info("获取到了current参数位置");
                Object arg = args[i];
                if (arg instanceof Integer){
                    log.info("页码为Integer类型");
                    if((int)arg <= 0){
                        arg=1;
                    }
                }
                if (arg instanceof String)
                    arg=1;
            }
        }
        return joinPoint.proceed(args);
    }
}
