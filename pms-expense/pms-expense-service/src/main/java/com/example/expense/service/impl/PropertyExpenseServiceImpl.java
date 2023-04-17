package com.example.expense.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.expense.mapper.PropertyExpenseMapper;
import com.example.expense.po.PropertyExpense;
import com.example.expense.service.PropertyExpenseService;
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
public class PropertyExpenseServiceImpl extends ServiceImpl<PropertyExpenseMapper, PropertyExpense> implements PropertyExpenseService {

}
