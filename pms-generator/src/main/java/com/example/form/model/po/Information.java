package com.example.form.model.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("information")
public class Information implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "iid", type = IdType.AUTO)
    private Integer iid;

    /**
     * 面积
     */
    private BigDecimal area;

    /**
     * 对应的地址
     */
    private Integer hid;

    /**
     * 状态
     */
    private String state;

    /**
     * 对应的住户
     */
    private Integer pid;

    /**
     * 门牌号
     */
    private String doorplate;
}
