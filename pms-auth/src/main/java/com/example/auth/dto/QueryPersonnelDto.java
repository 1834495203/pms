package com.example.auth.dto;

import lombok.Data;

/**
 * @author GLaDOS
 * @date 2023/4/12 16:52
 */
@Data
public class QueryPersonnelDto {

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 物业缴费状态
     */
    private String propertyExpenseState;
}
