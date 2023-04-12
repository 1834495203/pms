package com.example.auth.controller;

import com.example.auth.dto.QueryPersonnelDto;
import com.example.auth.dto.ResultUserBaseInfo;
import com.example.auth.po.Proprietor;
import com.example.auth.service.AdministratorService;
import com.example.auth.service.ProprietorService;
import com.example.config.AuthThreadLocal;
import com.example.exception.Error;
import com.example.model.PageParams;
import com.example.model.PageResult;
import com.example.model.RestResponse;
import com.example.model.UserThreadLocalDto;
import com.example.utils.IsAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @return ResultUserBaseInfo
     */
    @ApiOperation("获取管理员用户基本信息")
    @RequestMapping(value = "info/auth/{id}", method = RequestMethod.GET)
    public ResultUserBaseInfo getAdminBaseInfoById(@PathVariable Integer id){
        return administratorService.getBaseInfoById(id);
    }

    /**
     * 获取业主用户基本信息
     * @param id id
     * @return ResultUserBaseInfo
     */
    @ApiOperation("获取业主用户基本信息")
    @RequestMapping(value = "info/prop/{id}", method = RequestMethod.GET)
    public ResultUserBaseInfo getPropBaseInfoById(@PathVariable Integer id){
        return proprietorService.getUserBaseInfo(id);
    }

    /**
     * 获取全部用户信息
     * @return RR
     */
    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/info/prop/{pageNo}", method = RequestMethod.POST)
    public PageResult<Proprietor> selectProprietors(@RequestBody QueryPersonnelDto queryPersonnelDto,
                                                    @PathVariable Long pageNo){
        PageParams pageParams = new PageParams(pageNo);
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        IsAuth.init(authThreadLocal).or("900").start();
        return proprietorService.getListProprietor(pageParams, queryPersonnelDto);
    }
}
