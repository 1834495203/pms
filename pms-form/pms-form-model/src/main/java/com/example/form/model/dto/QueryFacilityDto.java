package com.example.form.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author GLaDOS
 * @date 2023/3/24 0:12
 */
@Data
public class QueryFacilityDto {
    //TODO 查找设施的dto类

    /**
     * 设施名称
     */
    private String name;

    /**
     * 状态
     */
    private String state;

    /**
     * 负责人
     */
    private Integer inChargeId;

    /**
     * 设施类型
     */
    private String type;

    /**
     * 设施创建时间
     */
    private LocalDateTime createDate;

    /**
     * 查询时间的条件
     * -1 之前; 0 等于; 1 之后
     */
    private Integer queryTime;

    /**
     * 设施所在的楼栋
     */
    private String building;

    /**
     * 设施所在的单元
     */
    private String unit;

    /**
     * 设施所在的部门
     */
    private String section;
}
