package com.example.form.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 房产信息
 * </p>
 *
 * @author GLaDOS
 */
@Data
@TableName("estate")
public class Estate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 房产信息
     */
    @TableId(value = "eid", type = IdType.AUTO)
    private Integer eid;

    /**
     * 楼栋
     */
    private Integer building;

    /**
     * 单元
     */
    private Integer unit;

    /**
     * 楼层
     */
    private Integer floor;

    /**
     * 负责人
     */
    private Integer inChargeId;

    /**
     * 人数
     */
    private Integer total;


}
