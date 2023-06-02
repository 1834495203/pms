package com.example.auth.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author GLaDOS
 * @date 2023/5/23 22:40
 */
@Data
public class VisitorPostDto {

    /**
     * 姓名
     */
    private String name;

    /**
     * 访问的门牌号
     */
    private String[] requiredDoorplate;

    /**
     * 访问的时间
     */
    private LocalDateTime[] requiredTime;

    /**
     * 状态
     */
    private String state;

    /**
     * 车牌号
     */
    private String licensePlate;

    /**
     * 性别
     */
    private String gender;

    /**
     * 离开时间
     */
    private LocalDateTime leavingTime;
}
