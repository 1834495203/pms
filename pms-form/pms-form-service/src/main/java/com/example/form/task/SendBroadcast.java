package com.example.form.task;

import com.example.config.AuthThreadLocal;
import com.example.form.service.BroadcastService;
import com.example.form.websocket.BroadcastWebsocket;
import com.example.model.UserThreadLocalDto;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

/**
 * 测试 定时发送广播
 * @author GLaDOS
 * @date 2023/5/14 13:14
 */
@Slf4j
@Component
public class SendBroadcast implements SchedulingConfigurer {

    @Autowired
    private BroadcastService broadcastService;

    @Autowired
    private BroadcastWebsocket broadcastWebsocket;

    @Scheduled(cron = "* 0/1 * * * ?")
    public void sendMessage(){
        if (broadcastWebsocket.getSessionPool().get("1") != null)
            broadcastWebsocket.sendOneMessage("1", "测试后端发送信息");
    }

    @Override
    public void configureTasks(@NotNull ScheduledTaskRegistrar scheduledTaskRegistrar) {
    }
}
