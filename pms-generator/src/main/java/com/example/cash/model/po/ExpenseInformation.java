package com.example.cash.model.po;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

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
