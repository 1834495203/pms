package com.example.form.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author GLaDOS
 */
@Data
@TableName("facility")
public class Facility implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 基础设施信息id
     */
    @TableId(value = "fid", type = IdType.AUTO)
    private Integer fid;

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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

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

    /**
     * 唯一辨识id
     */
    private Integer soleId;


}
