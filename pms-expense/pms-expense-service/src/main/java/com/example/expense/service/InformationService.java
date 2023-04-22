package com.example.expense.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.expense.po.ExpenseInformation;
import com.example.model.RestResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GLaDOS
 * @since 2023-04-19
 */
public interface InformationService extends IService<ExpenseInformation> {

    /**
     * 添加缴费信息
     * @param information 缴费信息
     * @return RR
     */
    RestResponse<ExpenseInformation> insertExpenseInfo(ExpenseInformation information);

}
