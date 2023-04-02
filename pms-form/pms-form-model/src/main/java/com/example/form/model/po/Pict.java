package com.example.form.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author GLaDOS
 */
@Data
@TableName("pict")
public class Pict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 相关图片表
     */
    @TableId(value = "pid", type = IdType.AUTO)
    private Integer pid;

    /**
     * 图片原名
     */
    private String name;

    /**
     * 图片在minio的地址
     */
    private String objectName;

    /**
     * 对应的表
     */
    private String type;

    /**
     * 对应的id
     */
    private Integer profileId;


}
