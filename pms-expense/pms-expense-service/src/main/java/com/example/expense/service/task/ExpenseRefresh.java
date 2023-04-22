package com.example.expense.service.task;

import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 每月重置物业费状态
 * @author GLaDOS
 * @date 2023/4/16 22:36
 */
@Slf4j
@EnableScheduling
@Configuration
public class ExpenseRefresh {

    @Scheduled(cron = "1 * * * * ?")
    private void configureTasks(){
        log.info("执行任务中...");
    }
}
