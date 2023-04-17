package com.example.expense.service.impl;

import com.example.expense.model.po.PropertyExpense;
import com.example.expense.mapper.PropertyExpenseMapper;
import com.example.expense.service.PropertyExpenseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

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
