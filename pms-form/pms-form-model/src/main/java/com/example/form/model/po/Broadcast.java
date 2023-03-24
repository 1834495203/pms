package com.example.form.model.po;

import com.baomidou.mybatisplus.annotation.*;
import com.example.utils.Auth;
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
@TableName("broadcast")
public class Broadcast implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公告id
     */
    @TableId(value = "bid", type = IdType.AUTO)
    private Integer bid;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 对应发放的人员
     */
    private String correspond;

    /**
     * 发送人id
     */
    private Integer pubilsherId;

    /**
     * 状态
     */
    private String state;

    /**
     * 媒体信息
     */
    private String profile;

    /**
     * 发表时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;


}
