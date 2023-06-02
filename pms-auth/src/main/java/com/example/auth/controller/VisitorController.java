package com.example.auth.controller;

import com.example.auth.dto.QueryVisitorDto;
import com.example.auth.dto.ResultVisitorDto;
import com.example.auth.dto.VisitorPostDto;
import com.example.auth.mapper.VisitorMapper;
import com.example.auth.po.Visitor;
import com.example.auth.service.VisitorService;
import com.example.config.AuthThreadLocal;
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
 * @author GLaDOS
 * @date 2023/5/23 19:15
 */
@Api(value = "访客相关接口", tags = "访客相关接口")
@Slf4j
@RestController
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @ApiOperation("发布访客信息")
    @RequestMapping(value = "visitor/post", method = RequestMethod.POST)
    public RestResponse<Visitor> postVisitorInfo(@RequestBody VisitorPostDto visitor){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        IsAuth.init(authThreadLocal).or("900").start();
        log.info(visitor.toString());
        return visitorService.postVisitorInfo(visitor);
    }

    @ApiOperation("查询并返回访客信息")
    @RequestMapping(value = "visitor/{pageNo}", method = RequestMethod.POST)
    public PageResult<ResultVisitorDto> getVisitorInfo(@RequestBody QueryVisitorDto queryVisitorDto, @PathVariable Long pageNo){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        IsAuth.init(authThreadLocal).or("900").start();
        PageParams pageParams = new PageParams(pageNo);
        return visitorService.getVisitorInfo(queryVisitorDto, pageParams);
    }

    @ApiOperation("查询并返回访客信息")
    @RequestMapping(value = "visitor/{doorplate}", method = RequestMethod.GET)
    public List<Visitor> getVisitorByDoorplate(@PathVariable String doorplate){
        return visitorService.getVisitorByDoorplate(doorplate);
    }
}
