package com.valten.aspect;

import com.valten.anno.AuthPermission;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.nio.file.AccessDeniedException;

@Aspect
@Component
public class AuthAspect {

    @Before("execution(* com.valten.service.*Service.*(..))")
    public void before(JoinPoint joinPoint) throws AccessDeniedException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        AuthPermission annotation = method.getAnnotation(AuthPermission.class);
        if (annotation != null) {
            // 取出要校验的参数所在的位置
            int idx = annotation.idx();
            Object[] args = joinPoint.getArgs();
            // 取出要检验的参数
            int userId = (Integer) args[idx];
            if (userId != 1) {
                throw new AccessDeniedException("您无权操作接口：" + method.getName());
            }
        }
    }
}
