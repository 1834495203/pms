package com.example.form.model.po;

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
@TableName("house")
public class House implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "hid", type = IdType.AUTO)
    private Integer hid;

    /**
     * 单位
     */
    private String label;

    /**
     * 值
     */
    private String value;

    /**
     * 父节点
     */
    private String parent;

    /**
     * 代号
     */
    private String type;


}
