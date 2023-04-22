package com.example.expense.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.expense.mapper.RecordMapper;
import com.example.expense.po.Record;
import com.example.expense.service.RecordService;
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
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

}
