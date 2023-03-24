package com.example.form.model.dto;

import lombok.Data;

/**
 * 更新公告内容
 * @author GLaDOS
 * @date 2023/3/24 22:35
 */
@Data
public class UpdateBroadcastDto {

    /**
     * 公告id
     */
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
     * 状态
     */
    private String state;

    /**
     * 媒体信息
     */
    private String profile;
}
