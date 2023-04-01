package com.example.form.model.dto;

import com.example.utils.Auth;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 查询公告条件
 * @author GLaDOS
 * @date 2023/3/25 0:04
 */
@Data
public class QueryBroadcastDto {

    /**
     * 标题
     */
    private String title;

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
    private LocalDateTime createDate;

    /**
     * 查询时间的条件
     * -1 之前; 0 等于; 1 之后
     */
    private Integer queryTime;
}
