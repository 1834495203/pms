package com.example.utils;

import java.lang.annotation.*;

/**
 * 权限注释
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Auth {
    //表明该字段或者类的访问权限
    String auth();
    //表明对类访问时排除指定的字段
    String[] exclude() default {};
}
