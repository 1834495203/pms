package com.example.personnel;

import com.example.auth.config.impl.JwtConfigBase;
import com.example.exception.GlobalExceptionHandler;
import com.example.utils.HasAuth;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author GLaDOS
 * @date 2023/4/1 14:04
 */
@Import({JwtConfigBase.class, GlobalExceptionHandler.class, HasAuth.class})
@EnableSwagger2Doc
@SpringBootApplication
public class PersonnelApplication {
    public static void main(String[] args) {
        SpringApplication.run(PersonnelApplication.class, args);
    }
}
