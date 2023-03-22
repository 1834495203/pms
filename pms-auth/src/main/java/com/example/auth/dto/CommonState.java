package com.example.auth.dto;

public enum CommonState {
    LOGIN_SUCCESS("登录成功"),
    LOGIN_FAILED("登录失败"),
    REGISTER_SUCCESS("注册成功"),
    REGISTER_FAILED("注册失败");

    private final String msg;

    CommonState(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
