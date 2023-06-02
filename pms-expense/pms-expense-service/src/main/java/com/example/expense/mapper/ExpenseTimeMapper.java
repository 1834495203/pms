package com.example.expense.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.expense.po.ExpenseTime;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author GLaDOS
 */
@Mapper
public interface ExpenseTimeMapper extends BaseMapper<ExpenseTime> {

    ExpenseTime selectById(Integer id);

}
