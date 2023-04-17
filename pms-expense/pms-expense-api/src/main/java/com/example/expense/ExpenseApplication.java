package com.example.expense;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author GLaDOS
 * @date 2023/4/16 22:19
 */
@MapperScan("com.example.expense.mapper")
@SpringBootApplication
public class ExpenseApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExpenseApplication.class, args);
    }
}
