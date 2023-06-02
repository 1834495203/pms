package com.example.cash.model.po;

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
@TableName("expense_time")
public class ExpenseTime implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 刷新时间
     */
    private String time;

    /**
     * 刷新id
     */
    private Integer eid;

    /**
     * 刷新的名称
     */
    private String title;


}
