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
@TableName("complaint")
public class Complaint implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 投诉id
     */
    @TableId(value = "cid", type = IdType.AUTO)
    private Integer cid;

    /**
     * 发送人信息
     */
    private Integer pubilsherId;

    /**
     * 内容
     */
    private String content;

    /**
     * 状态 已解决? 未解决?
     */
    private String state;

    /**
     * 上报时间
     */
    private LocalDateTime createTime;

    /**
     * 处理过程
     */
    private String process;

    /**
     * 图片信息
     */
    private String profile;

    /**
     * 解决人id
     */
    private Integer solverId;

    /**
     * 标题
     */
    private String title;


}
