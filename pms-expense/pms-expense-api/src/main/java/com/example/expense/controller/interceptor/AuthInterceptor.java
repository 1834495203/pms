package com.example.expense.controller.interceptor;

import com.example.auth.config.JwtConfig;
import com.example.config.AuthThreadLocal;
import com.example.exception.PMSException;
import com.example.model.UserThreadLocalDto;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器将用户的auth和id保存在threadLocal中
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Resource(name = "JwtConfigBase")
    private JwtConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置一个默认用户信息 999表示未登录用户
        UserThreadLocalDto userThreadLocalDto = new UserThreadLocalDto(-1, "999");
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        authThreadLocal.set(userThreadLocalDto);
        String token = request.getHeader(jwtConfig.getHeader());
        if (token != null){
            String auth = jwtConfig.getAuth(token);
            try{
                //防止token错误, 无法获取其中的内容
                Claims tokenClaim = jwtConfig.getTokenClaim(token);
                Integer id = tokenClaim.get("id", Integer.class);
                if (auth != null){
                    userThreadLocalDto.setAuth(auth);
                    userThreadLocalDto.setId(id);
                    authThreadLocal.set(userThreadLocalDto);
                    return true;
                }
            }catch (PMSException e){
                log.error(e.getErrorMsg());
                return true;
            }
        }
        //没有token
        return true;
    }
}
