package com.example.expense.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.exception.Error;
import com.example.exception.PMSException;
import com.example.expense.mapper.ExpenseInformationMapper;
import com.example.expense.po.ExpenseInformation;
import com.example.expense.service.InformationService;
import com.example.model.RestResponse;
import com.example.model.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GLaDOS
 */
@Slf4j
@Service
public class InformationServiceImpl extends ServiceImpl<ExpenseInformationMapper, ExpenseInformation> implements InformationService {

    @Autowired
    private ExpenseInformationMapper informationMapper;

    public RestResponse<ExpenseInformation> insertExpenseInfo(ExpenseInformation information){
        String id = DateUtil.format(new Date(System.currentTimeMillis()), "yyyyMM");
        information.setTid(id);
        ExpenseInformation informationFromDb = informationMapper.selectById(id);
        if (informationFromDb != null)
            throw new PMSException("缴费信息重复");
        if (informationMapper.insert(information) == 1)
            return RestResponse.success(information, "添加成功", Valid.DATABASE_INSERT_SUCCESS);
        return RestResponse.validFail("添加失败", Error.DATABASE_INSERT_FAILED);
    }
}
