package com.example.form.config.auth;

import com.example.config.AuthThreadLocal;
import com.example.model.UserThreadLocalDto;
import com.example.utils.IsAuth;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author GLaDOS
 * @date 2023/5/13 17:07
 */
@Slf4j
@Aspect
@Component
public class RecognizeAuth {



    //织入所有接口
    @Pointcut("execution(* com.example.form.controller.*.*(..))")
    public void pt(){}

    //接口环绕
    @Around("pt()")
    public Object AroundAuth(ProceedingJoinPoint pjp) throws Throwable {
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        WithAuth declaredAnnotation = signature.getMethod().getDeclaredAnnotation(WithAuth.class);
        IsAuth.init(authThreadLocal).or(declaredAnnotation.orAuth()).not(declaredAnnotation.andAuth()).start();
        return pjp.proceed();
    }
}
