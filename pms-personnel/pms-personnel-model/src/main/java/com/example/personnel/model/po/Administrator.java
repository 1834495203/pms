package com.example.personnel.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("administrator")
public class Administrator implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员id
     */
    @TableId(value = "aid", type = IdType.AUTO)
    private Integer aid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 权限
     */
    private String authority;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 雇佣时间
     */
    private LocalDateTime hiredate;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 图片信息
     */
    private String profile;


}
