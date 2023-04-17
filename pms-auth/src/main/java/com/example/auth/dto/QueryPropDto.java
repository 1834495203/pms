package com.example.auth.dto;

import com.example.auth.po.Proprietor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author GLaDOS
 * @date 2023/4/13 20:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QueryPropDto extends Proprietor {

    private List<Integer> addressList;
}
