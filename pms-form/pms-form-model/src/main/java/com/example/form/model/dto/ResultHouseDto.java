package com.example.form.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 楼栋信息
 * @author GLaDOS
 * @date 2023/4/9 21:03
 */
@Data
public class ResultHouseDto {

    /**
     * 楼栋id
     */
    private Integer hid;

    /**
     * 表中最后一个数据的id
     */
    private Integer lastId;

    /**
     * 楼栋名
     */
    private String label;

    /**
     * 楼栋值
     */
    private String value;

    /**
     * 展示信息的label
     */
    private String toDisLabel;

    /**
     * 业主id
     * if contains
     */
    private String propId;

    /**
     * 楼栋子节点
     */
    private List<ResultHouseDto> children;
}
