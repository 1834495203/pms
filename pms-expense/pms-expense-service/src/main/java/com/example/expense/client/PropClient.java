package com.example.expense.client;

import com.example.expense.client.callback.PropClientCallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author GLaDOS
 * @date 2023/5/28 18:57
 */
@FeignClient(value = "auth-service", fallbackFactory = PropClientCallback.class)
public interface PropClient {

    @RequestMapping(value = "/info/alterState", method = RequestMethod.GET)
    void alterExpenseState();
}
