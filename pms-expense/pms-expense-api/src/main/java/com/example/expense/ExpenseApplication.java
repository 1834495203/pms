package com.example.expense;

import com.example.auth.config.impl.JwtConfigBase;
import com.example.exception.GlobalExceptionHandler;
import com.example.utils.HasAuth;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author GLaDOS
 * @date 2023/4/16 22:19
 */
@Import({JwtConfigBase.class, GlobalExceptionHandler.class, HasAuth.class})
@EnableSwagger2Doc
@MapperScan("com.example.expense.mapper")
@SpringBootApplication
public class ExpenseApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExpenseApplication.class, args);
    }
}
