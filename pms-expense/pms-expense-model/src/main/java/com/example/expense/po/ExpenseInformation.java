package com.example.expense.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author GLaDOS
 */
@Data
@TableName("expense_information")
public class ExpenseInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String tid;

    /**
     * 某年某月需缴费金额
     */
    private LocalDate date;

    /**
     * 金额
     */
    private BigDecimal price;

    /**
     * 附加说明
     */
    private String addition;


}
