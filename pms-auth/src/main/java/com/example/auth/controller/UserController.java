package com.example.auth.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.example.auth.config.JwtConfig;
import com.example.auth.po.Administrator;
import com.example.auth.po.Proprietor;
import com.example.auth.po.Visitor;
import com.example.auth.service.AdministratorService;
import com.example.auth.service.ProprietorService;
import com.example.auth.dto.UserResponse;
import com.example.model.RestResponse;
import com.example.model.Valid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 与用户权限相关的api
 */
@Api(value = "用户统一登录与注册", tags = "用户统一登录与注册")
@RestController
public class UserController {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private ProprietorService proprietorService;

    @Resource(name = "JwtConfigBase")
    private JwtConfig jwtConfig;

    @ApiOperation("测试token中的信息")
    @RequestMapping(value = "/test/{token}", method = RequestMethod.GET)
    public String testToken(@PathVariable String token){
        return jwtConfig.getTokenClaim(token).getSubject();
    }

    @ApiOperation("接口测试")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public RestResponse<Visitor> testFront(){
        Visitor visitor = new Visitor();
        visitor.setName("Marias");
        visitor.setGender("女");
        visitor.setLeavingTime(LocalDateTime.now());
        return RestResponse.success(visitor, "ok", Valid.SUCCESS);
    }

    @ApiOperation("管理员登录api")
    @RequestMapping(value = "/login/admin", method = RequestMethod.POST)
    public UserResponse loginForAdmin(@RequestBody Administrator administrator){
        //将密码加密
        String s = DigestUtil.md5Hex(administrator.getPassword());
        administrator.setPassword(s);
        String token = administratorService.loginForAdmin(administrator);
        return UserResponse.loginSuccess(token);
    }

    @ApiOperation("管理员注册api")
    @RequestMapping(value = "/register/admin", method = RequestMethod.POST)
    public UserResponse registerForAdmin(@RequestBody Administrator administrator){
        if (administrator.getHiredate() == null)
            administrator.setHiredate(LocalDateTime.now());
        String password = administrator.getPassword();
        //密码加密
        String s = DigestUtil.md5Hex(password);
        administrator.setPassword(s);
        if (administratorService.registerForAdmin(administrator))
            return UserResponse.registerSuccess();
        return UserResponse.registerFail();
    }

    @ApiOperation("业主登录api")
    @RequestMapping(value = "/login/proprietor", method = RequestMethod.POST)
    public UserResponse loginForProprietor(@RequestBody Proprietor proprietor) {
        //将密码加密
        String s = DigestUtil.md5Hex(proprietor.getPassword());
        proprietor.setPassword(s);
        String token = proprietorService.loginForProprietor(proprietor);
        return UserResponse.loginSuccess(token);
    }

    @ApiOperation("业主注册api")
    @RequestMapping(value = "/register/proprietor", method = RequestMethod.POST)
    public UserResponse registerForProprietor(@RequestBody Proprietor proprietor) {
        if (proprietor.getCreateDate() == null)
            proprietor.setCreateDate(LocalDateTime.now());
        //将密码加密
        String s = DigestUtil.md5Hex(proprietor.getPassword());
        proprietor.setPassword(s);
        if (proprietorService.registerForProprietor(proprietor))
            return UserResponse.registerSuccess();
        return UserResponse.registerFail();
    }
}
