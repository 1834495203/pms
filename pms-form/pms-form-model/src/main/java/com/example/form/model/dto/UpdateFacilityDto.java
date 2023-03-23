package com.example.form.model.dto;

import com.example.utils.Auth;
import lombok.Data;

/**
 * 更新设施的内容
 * @author GLaDOS
 * @date 2023/3/23 23:36
 */
@Data
@Auth(auth = "90091", exclude = {"fid", "inChargeId"})
public class UpdateFacilityDto {

    /**
     * 基础设施信息id
     */
    private Integer fid;

    /**
     * 设施名称
     */
    private String name;

    /**
     * 状态
     * 前台权限
     */
    @Auth(auth = "90093")
    private String state;

    /**
     * 负责人
     * 前台权限
     */
    @Auth(auth = "90093")
    private Integer inChargeId;

    /**
     * 设施类型
     */
    private String type;

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
