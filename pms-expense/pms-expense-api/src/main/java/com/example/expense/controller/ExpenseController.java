package com.example.expense.controller;

import com.example.config.AuthThreadLocal;
import com.example.expense.po.ExpenseInformation;
import com.example.expense.service.InformationService;
import com.example.model.RestResponse;
import com.example.model.UserThreadLocalDto;
import com.example.utils.IsAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 物业缴费管理
 * @author GLaDOS
 * @date 2023/4/19 20:49
 */
@Slf4j
@RestController
public class ExpenseController {

    @Autowired
    private InformationService informationService;

    /**
     * 插入每月缴费信息
     * @param information 缴费信息
     * @return RR
     */
    @RequestMapping(value = "exp/info", method = RequestMethod.POST)
    public RestResponse<ExpenseInformation> insertExpenseInfo(@RequestBody ExpenseInformation information){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        IsAuth.init(authThreadLocal).or("900").start();
        return informationService.insertExpenseInfo(information);
    }
}
