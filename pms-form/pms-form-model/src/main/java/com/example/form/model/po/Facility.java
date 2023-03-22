package com.example.form.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 基础设施
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
     * 地址
     */
    private String address;


}
