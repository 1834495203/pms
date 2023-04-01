package com.example.auth.dto;

import lombok.Data;

/**
 * 保存在threadLocal中的用户信息
 */
@Data
public class UserThreadLocalDto {

    private Integer id;

    private String auth;

    public UserThreadLocalDto() {
    }

    public UserThreadLocalDto(Integer id, String auth) {
        this.id = id;
        this.auth = auth;
    }
}
