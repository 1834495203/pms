package com.example.expense.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.expense.service.PropertyExpenseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GLaDOS
 */
@Slf4j
@RestController
@RequestMapping("propertyExpense")
public class PropertyExpenseController {

    @Autowired
    private PropertyExpenseService  propertyExpenseService;
}
