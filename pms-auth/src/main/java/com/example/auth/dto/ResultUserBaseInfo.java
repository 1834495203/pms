package com.example.auth.dto;

import lombok.Data;

/**
 * @author GLaDOS
 * @date 2023/4/5 21:33
 */
@Data
public class ResultUserBaseInfo {

    /**
     * 用户名
     */
    private String username;

    /**
     * 头像
     */
    private String profile;

    /**
     * 权限
     */
    private String authority;

    /**
     * 身份
     */
    private String status;
}
