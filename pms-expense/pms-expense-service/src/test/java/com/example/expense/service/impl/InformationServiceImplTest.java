package com.example.expense.service.impl;

import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GLaDOS
 * @date 2023/4/19 21:11
 */
@SpringBootTest
class InformationServiceImplTest {

    @Test
    void testDate(){
        System.out.println(DateUtil.format(new Date(System.currentTimeMillis()), "yyyyMM"));
    }
}