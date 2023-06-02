package com.example.auth.dto;

import lombok.Data;

/**
 * @author GLaDOS
 * @date 2023/5/25 11:46
 */
@Data
public class QueryVisitorDto {

    /**
     * 姓名
     */
    private String name;

    /**
     * 门牌号
     */
    private String doorplate;

    /**
     * 车牌号
     */
    private String licensePlate;
}
