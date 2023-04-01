package com.example.form;

import com.example.auth.config.WebConfig;
import com.example.auth.config.impl.JwtConfigBase;
import com.example.auth.controller.interceptor.AuthInterceptor;
import com.example.exception.GlobalExceptionHandler;
import com.example.utils.HasAuth;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({JwtConfigBase.class, GlobalExceptionHandler.class, HasAuth.class})
@EnableSwagger2Doc
@SpringBootApplication
public class FormApplication {
    public static void main(String[] args) {
        SpringApplication.run(FormApplication.class, args);
    }
}
