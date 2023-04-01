package com.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * 全局异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //捕获PMSException可知异常
    @ExceptionHandler(PMSException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //返回异常信息
    @ResponseBody
    public RestErrorResponse doOSException(PMSException e){
        String errorMsg = e.getErrorMsg();
        Error error = e.getCommonError();

        log.error("已知异常: "+errorMsg);
        e.printStackTrace();

        return new RestErrorResponse(errorMsg, error);
    }

    //捕获不可知异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //服务器内部错误
    @ResponseBody
    public RestErrorResponse doException(Exception e){
        String errorMsg = e.getMessage();

        log.error("未知的异常: "+errorMsg);
        e.printStackTrace();

        return new RestErrorResponse(errorMsg);
    }

    //捕获变量校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RestErrorResponse doMethodArgumentNotValidException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        //校验的错误信息
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        //收集错误
        StringBuffer errors = new StringBuffer();
        fieldErrors.forEach(error-> errors.append(error.getDefaultMessage()).append(", "));

        return new RestErrorResponse(errors.toString());
    }
}
