package com.example.personnel.controller;

import com.example.model.RestResponse;
import com.example.personnel.model.dto.ResultUserBaseInfo;
import com.example.personnel.service.AdministratorService;
import com.example.personnel.service.ProprietorService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GLaDOS
 * @date 2023/4/5 21:35
 */
@Api("获取用户基本信息")
@Slf4j
@RestController
public class UserInfoController {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private ProprietorService proprietorService;

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
