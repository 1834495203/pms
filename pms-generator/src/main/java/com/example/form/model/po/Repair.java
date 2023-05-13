package com.example.form.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
@TableName("repair")
public class Repair implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 维修id
     */
    @TableId(value = "rid", type = IdType.AUTO)
    private Integer rid;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态
     */
    private String state;

    /**
     * 上报时间
     */
    private LocalDateTime createTime;

    /**
     * 上报人id
     */
    private Integer pubilsherId;

    /**
     * 负责人
     */
    private Integer inChargeId;

    /**
     * 图片信息 or 视频
     */
    private String profile;

    /**
     * 解决的时间
     */
    private LocalDateTime solvedTime;

    /**
     * 设施类型
     */
    private String type;

    /**
     * 预约时间
     */
    private LocalDate appointment;


}
