package com.example.model;

import com.example.exception.Error;
import lombok.Data;
import lombok.ToString;
import org.omg.PortableInterceptor.SUCCESSFUL;

/**
 * 通用结果类型
 * @author GLaDOS
 */

@Data
@ToString
public class RestResponse<T> {

    /**
    * 响应编码
    */
    private Code code;

    /**
    * 响应提示信息
    */
    private String msg;

    /**
    * 响应内容
    */
    private T result;


    public RestResponse() {
    this(Valid.SUCCESS, "success");
    }

    public RestResponse(Code code, String msg) {
    this.code = code;
    this.msg = msg;
    }

    /**
    * 错误信息的封装
    *
    * @param msg 讯息
    * @param <T> 对象
    * @return RestResponse
    */
    public static <T> RestResponse<T> validFail(String msg) {
        RestResponse<T> response = new RestResponse<T>();
        response.setCode(Error.FAILED);
        response.setMsg(msg);
        return response;
    }

    public static <T> RestResponse<T> validFail(String msg, Code code) {
        RestResponse<T> response = new RestResponse<T>();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    public static <T> RestResponse<T> validFail(T result, String msg) {
        RestResponse<T> response = new RestResponse<T>();
        response.setCode(Error.FAILED);
        response.setResult(result);
        response.setMsg(msg);
        return response;
    }

    public static <T> RestResponse<T> validFail(T result, String msg, Code code) {
        RestResponse<T> response = new RestResponse<T>();
        response.setCode(code);
        response.setResult(result);
        response.setMsg(msg);
        return response;
    }

    /**
    * 添加正常响应数据（包含响应内容）
    *
    * @return RestResponse Rest服务封装相应数据
    */
    public static <T> RestResponse<T> success(T result) {
        RestResponse<T> response = new RestResponse<T>();
        response.setResult(result);
        response.setCode(Valid.SUCCESS);
        return response;
    }

    public static <T> RestResponse<T> success(T result, String msg) {
        RestResponse<T> response = new RestResponse<T>();
        response.setResult(result);
        response.setMsg(msg);
        response.setCode(Valid.SUCCESS);
        return response;
    }

    public static <T> RestResponse<T> success(T result, String msg, Code code) {
        RestResponse<T> response = new RestResponse<T>();
        response.setCode(code);
        response.setResult(result);
        response.setMsg(msg);
        return response;
    }

    /**
    * 添加正常响应数据（不包含响应内容）
    *
    * @return RestResponse Rest服务封装相应数据
    */
    public static <T> RestResponse<T> success() {
    return new RestResponse<T>();
    }

    public Boolean isSuccessful() {
    return this.code == Valid.SUCCESS;
    }
}