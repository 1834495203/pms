package com.example.form.model.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 前端发送的投诉信息表
 */
@Data
public class PostComplaintDto {

    /**
     * 发送人信息
     */
    @ApiParam(value = "发送人id")
    private Integer pubilsherId;

    /**
     * 内容
     */
    @ApiParam(value = "投诉内容", required = true)
    private String content;

    /**
     * 上报时间
     */
    @ApiParam(value = "上报时间")
    private LocalDateTime createTime;

    /**
     * 图片信息
     */
    @ApiParam(value = "图片信息")
    private String profiles;

    /**
     * 标题
     */
    @ApiParam(value = "标题", required = true)
    private String title;
}
