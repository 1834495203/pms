package com.example.form.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 公告
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
     * 图片信息
     */
    private String profile;


}
