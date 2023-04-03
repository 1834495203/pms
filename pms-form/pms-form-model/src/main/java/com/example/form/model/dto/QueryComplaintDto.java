package com.example.form.model.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 查询投诉表需要的信息
 */
@Data
public class QueryComplaintDto {

    /**
     * 状态 已解决? 未解决? 40010, 40011
     */
    private String state;

    /**
     * 上报时间
     */
    @ApiModelProperty(value = "上报时间")
    private LocalDateTime createTime;

    /**
     * 查询时间的条件
     * -1 之前; 0 等于; 1 之后
     */
    private Integer queryTime;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 发送人信息
     */
    private Integer pubilsherId;

    /**
     * 发布日期
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDate createDate;
}
