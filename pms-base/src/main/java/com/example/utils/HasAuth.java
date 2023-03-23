package com.example.utils;

import com.example.exception.Error;
import com.example.exception.PMSException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 在dto上添加注释, 判断字段的访问权限, 返回字段被筛选后的类
 * 在类上添加注释, 表明该类的访问权限
 * @param <T> 鉴权dto类
 * @author GLaDOS
 */
@Component
public class HasAuth <T> {

    /**
     * 用户权限
     */
    private String userAuth;

    /**
     * dto实体
     */
    private T entity;

    /**
     * dto类
     */
    private Class<T> clazz;

    public HasAuth() {}

    public HasAuth(String userAuth, T entity, Class<T> clazz) {
        this.userAuth = userAuth;
        this.entity = entity;
        this.clazz = clazz;
    }

    public T afterAuth(){
        return this.afterAuth(userAuth, entity, clazz);
    }

    public T afterAuth(String userAuth, T entity, Class<T> clazz){
        try{
            T instance = clazz.getDeclaredConstructor().newInstance();
            String superAuth = null;
            String[] exclude = {};
            Auth superAnnotation;
            if ((superAnnotation = entity.getClass().getAnnotation(Auth.class)) != null){
                superAuth = superAnnotation.auth();
                exclude = superAnnotation.exclude();
            }
            for (Field df : entity.getClass().getDeclaredFields()) {
                df.setAccessible(true);
                Auth annotation = df.getAnnotation(Auth.class);
                Field newDf = instance.getClass().getDeclaredField(df.getName());
                newDf.setAccessible(true);
                if (annotation == null) newDf.set(instance, df.get(entity));
                else if (userAuth.contains(annotation.auth()) ||
                        superAuth != null && userAuth.contains(superAuth) &&
                                !isContains(df.getName(), exclude))
                    newDf.set(instance, df.get(entity));
            }
            return instance;
        }catch (Exception e){
            e.printStackTrace();
            throw new PMSException("字段权限读取错误", Error.UNKNOWN_FAILED);
        }
    }

    private boolean isContains(String s, String[] arr) {
        return Arrays.asList(arr).contains(s);
    }
}
