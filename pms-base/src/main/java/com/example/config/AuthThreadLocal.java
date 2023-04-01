package com.example.config;

import com.example.model.UserThreadLocalDto;
import org.springframework.stereotype.Component;

/**
 * 保存当前访问用户的信息
 * UserThreadLocalDto包含: id, auth
 */
@Component
public class AuthThreadLocal {

    private static final ThreadLocal<UserThreadLocalDto> authThreadLocal = new ThreadLocal<>();

    public void setAuth(UserThreadLocalDto user){
        authThreadLocal.set(user);
    }

    public UserThreadLocalDto getAuth(){
        return authThreadLocal.get();
    }

    public static ThreadLocal<UserThreadLocalDto> getAuthThreadLocal(){
        return authThreadLocal;
    }
}
