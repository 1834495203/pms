package com.example.utils;

import com.example.exception.Error;
import com.example.exception.PMSException;
import com.example.model.UserThreadLocalDto;

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

    public IsAuth not(String ...auth){
        //只要条件中有一个是的话, 则抛出未授权
        if (auth.length == 0) return isAuth;
        for (String s : auth) {
            if (userAuth.contains(s)) {
                decide = false;
                break;
            }
        }
        return isAuth;
    }

    public IsAuth or(String auth){
        //只要条件中有一个是的话, 则不抛出未授权
        if (decide) return isAuth;
        if (userAuth.contains(auth)) decide = true;
        return isAuth;
    }

    public IsAuth or(String ...auth){
        if (auth.length == 0) return isAuth;
        //只要条件中有一个是的话, 则不抛出未授权
        if (decide) return isAuth;
        for (String s : auth)
            if (userAuth.contains(s)) {
                decide = true;
                break;
            }
        return isAuth;
    }

    public void start(){
        //判断权限
        if (!decide) throw new PMSException("没有权限调用此api", Error.UNAUTHORIZED);
    }
}
