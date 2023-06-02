package com.example.expense.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.expense.mapper.ExpenseTimeMapper;
import com.example.expense.po.ExpenseTime;
import com.example.expense.service.ExpenseTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GLaDOS
 */
@Slf4j
@Service
public class ExpenseTimeServiceImpl extends ServiceImpl<ExpenseTimeMapper, ExpenseTime> implements ExpenseTimeService {

}
