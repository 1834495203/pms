package com.example.form;

import com.example.auth.config.impl.JwtConfigBase;
import com.example.exception.GlobalExceptionHandler;
import com.example.utils.HasAuth;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients(basePackages = "com.example.form.client")
@Import({JwtConfigBase.class, GlobalExceptionHandler.class, HasAuth.class})
@EnableSwagger2Doc
@EnableScheduling
@SpringBootApplication
public class FormApplication {
    public static void main(String[] args) {
        SpringApplication.run(FormApplication.class, args);
    }
}
