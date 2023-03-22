package com.example.auth.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author GLaDOS
 */
@Data
@TableName("proprietor")
public class Proprietor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业主id
     */
    @TableId(value = "pid", type = IdType.AUTO)
    private Integer pid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 地址
     */
    private String address;

    /**
     * 身份
     */
    private String status;

    /**
     * 入住时间
     */
    private LocalDateTime checkInDate;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 图片信息
     */
    private String profile;

    /**
     * 性别
     */
    private String gender;

    /**
     * 物业费状态
     */
    private Integer propertyExpenseState;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;


}
