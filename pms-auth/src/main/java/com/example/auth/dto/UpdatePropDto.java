package com.example.auth.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 修改用户信息dto
 * @author GLaDOS
 * @date 2023/4/13 19:23
 */
@Data
public class UpdatePropDto {

    /**
     * 业主id
     */
    private Integer pid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 入住时间
     */
    private LocalDateTime checkInDate;

    /**
     * 地址
     */
    private String address;

    /**
     * 性别
     */
    private String gender;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 身份证号
     */
    private String idCard;
}
