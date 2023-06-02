package com.example.expense.service.task;

import com.example.exception.PMSException;
import com.example.expense.client.PropClient;
import com.example.expense.mapper.ExpenseTimeMapper;
import com.example.expense.util.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

/**
 * 每月重置物业费状态
 * @author GLaDOS
 * @date 2023/4/16 22:36
 */
@Slf4j
@EnableScheduling
@Configuration
public class ExpenseRefresh implements SchedulingConfigurer {

    @Autowired
    private ExpenseTimeMapper expenseTimeMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private PropClient propClient;

    @Override
    public void configureTasks(@NotNull ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(
                ()->{
                    //工作内容
                    log.info("执行定时任务中: {}", expenseTimeMapper.selectById(1).getTitle());
                    propClient.alterExpenseState();
                },
                triggerContext -> {
                    String cron = null;
                    //获取锁
                    while (RedisLock.get("expense", redisTemplate).lock()){
                        log.info("获取所expense成功");
                        try{
                            cron = expenseTimeMapper.selectById(1).getTime();
                            if (cron.equals("")) throw new PMSException("暂无定时数据");
                        }catch (Exception e){
                            e.printStackTrace();
                            throw new PMSException("定时任务expense错误!!!");
                        }
                    }
                    if (cron == null) throw new PMSException("暂无定时数据");
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
    }
}
