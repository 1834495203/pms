package com.example.auth.dto;

import com.example.auth.po.Visitor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author GLaDOS
 * @date 2023/5/26 9:45
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResultVisitorDto extends Visitor {

    /**
     * 业主名
     */
    private String propName;
}
