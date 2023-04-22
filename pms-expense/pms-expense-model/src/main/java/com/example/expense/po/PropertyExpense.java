package com.example.expense.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author GLaDOS
 */
@Data
@TableName("property_expense")
public class PropertyExpense implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物业费的id
     */
    private Integer eid;

    /**
     * 业主的id
     */
    private Integer payerId;

    /**
     * 缴费的时间
     */
    private LocalDateTime createTime;

    /**
     * 物业费的状态
     */
    private String state;

    /**
     * 缴费的方法
     */
    private String method;

    /**
     * 应收金额
     */
    private BigDecimal payableAmount;

    /**
     * 实收金额
     */
    private BigDecimal received;


}
