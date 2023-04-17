package com.example.expense.service.task;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 每月重置物业费状态
 * @author GLaDOS
 * @date 2023/4/16 22:36
 */
@Slf4j
@Component
public class ExpenseRefresh {


    @XxlJob("testExpense")
    public void testExpense(){
        log.info("工作中...");
    }
}
