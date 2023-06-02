package com.example.auth.po;

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
@TableName("visitor")
public class Visitor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 访客信息id
     */
    @TableId(value = "vid", type = IdType.AUTO)
    private Integer vid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 访问的门牌号
     */
    private String requiredDoorplate;

    /**
     * 访问的时间
     */
    private LocalDateTime requiredTime;

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

    /**
     * 预计离开时间
     */
    private LocalDateTime requiredLeavingTime;
}
