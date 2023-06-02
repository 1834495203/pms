package com.example.expense.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.expense.mapper.ExpenseTimeMapper;
import com.example.expense.po.ExpenseTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GLaDOS
 * @date 2023/4/19 21:11
 */
@SpringBootTest
class InformationServiceImplTest {

    @Autowired
    private ExpenseTimeMapper expenseTimeMapper;

    @Test
    void testDate(){
        System.out.println(DateUtil.format(new Date(System.currentTimeMillis()), "yyyyMM"));
    }

    @Test
    void testQuery(){
        ExpenseTime expenseTime = expenseTimeMapper.selectById(1);
        System.out.println(expenseTime);
    }
}