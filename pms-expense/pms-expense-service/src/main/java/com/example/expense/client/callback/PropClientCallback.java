package com.example.expense.client.callback;

import com.example.exception.PMSException;
import com.example.expense.client.PropClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author GLaDOS
 * @date 2023/5/28 18:58
 */
@Slf4j
@Component
public class PropClientCallback implements FallbackFactory<PropClient> {

    @Override
    public PropClient create(Throwable throwable) {
        return () -> log.error("修改用户缴费状态失败!");
    }
}
