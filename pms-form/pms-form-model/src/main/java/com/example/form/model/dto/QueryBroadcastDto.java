package com.example.form.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
     * 0:所有；1:业主；2:员工
     */
    private String correspond;

    /**
     * 发送人id
     */
    private Integer pubilsherId;

    /**
     * 查询时间的条件
     */
    private List<LocalDateTime> queryDate;
}
