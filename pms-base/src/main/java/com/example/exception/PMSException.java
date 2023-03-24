package com.example.exception;

/**
 * 自定义异常
 */
public class PMSException extends RuntimeException {

    private String errorMsg;

    private Error error;

    private Integer errorCode;

    public PMSException() {
        super();
    }

    public PMSException(String message) {
        super(message);
        this.errorMsg = message;
    }

    public PMSException(Throwable cause) {
        super(cause);
    }

    public PMSException(String message, Error error) {
        super(message);
        this.errorMsg = message;
        this.error = error;
        this.errorCode = error.getCode();
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public Error getCommonError() {
        return error;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public static void cast(String msg){
        throw new PMSException(msg);
    }
}
