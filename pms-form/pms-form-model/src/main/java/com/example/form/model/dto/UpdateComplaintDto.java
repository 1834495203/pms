package com.example.form.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.utils.Auth;
import lombok.Data;

@Data
public class UpdateComplaintDto {

    /**
     * 投诉id
     */
    @TableId(value = "cid", type = IdType.AUTO)
    private Integer cid;

    /**
     * 发送人信息, 如果为业主修改是id与db不一致则禁止修改
     */
    private Integer pubilsherId;

    /**
     * 内容 仅业主修改
     */
    @Auth(auth = "910")
    private String content;

    /**
     * 状态 仅前台修改
     */
    @Auth(auth = "90093")
    private String state;

    /**
     * 处理过程 仅前台修改
     */
    @Auth(auth = "90093")
    private String process;

    /**
     * 解决人id 仅前台修改
     */
    @Auth(auth = "90093")
    private Integer solverId;

    /**
     * 标题 仅业主修改
     */
    @Auth(auth = "910")
    private String title;
}
