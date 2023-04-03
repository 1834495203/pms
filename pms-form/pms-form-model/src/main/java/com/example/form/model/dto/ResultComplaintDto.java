package com.example.form.model.dto;

import com.example.form.model.po.Complaint;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author GLaDOS
 * @date 2023/4/3 23:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResultComplaintDto extends Complaint {

    /**
     * 图片在minio的地址
     */
    private List<String> objectName;
}
