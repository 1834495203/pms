package com.example.model;

/**
 * 通用正确信息
 */
public enum Valid implements Code{

    SUCCESS(101),

    DATABASE_INSERT_SUCCESS(606),
    DATABASE_UPDATE_SUCCESS(605),
    DATABASE_DELETE_SUCCESS(604),
    DATABASE_SELECT_SUCCESS(603);

    private final Integer code;

    public Integer getCode() {
        return code;
    }

    Valid(Integer code) {
        this.code = code;
    }
}
