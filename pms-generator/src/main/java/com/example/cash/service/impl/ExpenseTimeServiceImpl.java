package com.example.cash.service.impl;

import com.example.cash.model.po.ExpenseTime;
import com.example.cash.mapper.ExpenseTimeMapper;
import com.example.cash.service.ExpenseTimeService;
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
public class ExpenseTimeServiceImpl extends ServiceImpl<ExpenseTimeMapper, ExpenseTime> implements ExpenseTimeService {

}
