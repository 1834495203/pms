package com.example.form.util;

import com.example.exception.Error;
import com.example.exception.PMSException;
import com.example.form.model.dto.UserThreadLocalDto;

/**
 * 判断用户权限
 */
public class IsAuth {

    private static final IsAuth isAuth = new IsAuth();

    private static String userAuth;

    //默认直接抛出
    private static Boolean decide = false;

    private IsAuth(){}

    public static IsAuth init(ThreadLocal<UserThreadLocalDto> threadLocal){
        decide = false;
        UserThreadLocalDto userThreadLocalDto = threadLocal.get();
        userAuth = userThreadLocalDto.getAuth();
        return isAuth;
    }

    public static IsAuth init(UserThreadLocalDto userDto){
        decide = false;
        userAuth = userDto.getAuth();
        return isAuth;
    }

    public static IsAuth init(String auth){
        decide = false;
        userAuth = auth;
        return isAuth;
    }

    public IsAuth not(String auth){
        //只要条件中有一个是的话, 则抛出未授权
        if (userAuth.contains(auth)) decide = false;
        return isAuth;
    }

    public IsAuth or(String auth){
        //只要条件中有一个是的话, 则不抛出未授权
        if (decide) return isAuth;
        if (userAuth.contains(auth)) decide = true;
        return isAuth;
    }

    public void start(){
        //判断权限
        if (!decide) throw new PMSException("没有权限调用此api", Error.UNAUTHORIZED);
    }
}
