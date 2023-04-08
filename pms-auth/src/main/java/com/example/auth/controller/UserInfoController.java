package com.example.auth.controller;

import com.example.auth.dto.ResultUserBaseInfo;
import com.example.auth.service.AdministratorService;
import com.example.auth.service.ProprietorService;
import com.example.config.AuthThreadLocal;
import com.example.exception.Error;
import com.example.model.RestResponse;
import com.example.model.UserThreadLocalDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息的CURD
 * @author GLaDOS
 * @date 2023/3/31 15:38
 */
@Api(value = "用户信息操作", tags = "用户信息操作")
@Slf4j
@RestController
public class UserInfoController {

    @Autowired
    private ProprietorService proprietorService;

    @Autowired
    private AdministratorService administratorService;

    /**
     * 根据token获取当前用户id
     * @return RR
     */
    @ApiOperation("根据token获取当前用户id")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public RestResponse<?> getCurrentUserInfo(){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        String auth = authThreadLocal.get().getAuth();
        Integer id = authThreadLocal.get().getId();
        //业主
        if (auth.contains("910"))
            return proprietorService.getCurrentProprietor(id, auth);
        //管理员
        else if (auth.contains("900"))
            return administratorService.getCurrentAdminInfo(id, auth);
        return RestResponse.validFail("没有权限!", Error.UNAUTHORIZED);
    }

    /**
     * 获取管理员用户基本信息
     * @param id id
     * @return RR
     */
    @RequestMapping(value = "info/auth/{id}", method = RequestMethod.GET)
    public ResultUserBaseInfo getAdminBaseInfoById(@PathVariable Integer id){
        return administratorService.getBaseInfoById(id);
    }

    @RequestMapping(value = "info/prop/{id}", method = RequestMethod.GET)
    public ResultUserBaseInfo getPropBaseInfoById(@PathVariable Integer id){
        return proprietorService.getUserBaseInfo(id);
    }
}
