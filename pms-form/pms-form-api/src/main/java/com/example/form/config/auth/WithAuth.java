package com.example.form.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 基于aop与注解实现权限认证
 * @author GLaDOS
 * @date 2023/5/13 17:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WithAuth {
    String[] orAuth() default {};

    String[] andAuth() default {};
}
