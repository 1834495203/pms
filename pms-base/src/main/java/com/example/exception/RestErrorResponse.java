package com.example.exception;

import lombok.Data;

import java.io.Serializable;

/**
 * 错误响应参数包装
 */
@Data
public class RestErrorResponse implements Serializable {

    private String errMessage;

    private Error error;

    private Integer errorCode;

    public RestErrorResponse(String errMessage){
        this.errMessage= errMessage;
    }

    public RestErrorResponse(String errMessage, Error error) {
        this.errMessage = errMessage;
        this.error = error;
        this.errorCode = error.getCode();
    }
}
