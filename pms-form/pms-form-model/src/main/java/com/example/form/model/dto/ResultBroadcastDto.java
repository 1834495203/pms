package com.example.form.model.dto;

import com.example.form.model.po.Broadcast;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author GLaDOS
 * @date 2023/4/5 18:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResultBroadcastDto extends Broadcast {

    /**
     * 图片在minio的地址
     */
    private List<String> objectName;

    /**
     * 发布人用户名
     */
    private String username;
}
