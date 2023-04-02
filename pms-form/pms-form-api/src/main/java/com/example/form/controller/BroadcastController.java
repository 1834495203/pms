package com.example.form.controller;

import com.example.config.AuthThreadLocal;
import com.example.form.model.dto.QueryBroadcastDto;
import com.example.form.model.dto.UpdateBroadcastDto;
import com.example.form.model.po.Broadcast;
import com.example.form.service.BroadcastService;
import com.example.form.util.IsAuth;
import com.example.model.PageParams;
import com.example.model.PageResult;
import com.example.model.RestResponse;
import com.example.model.UserThreadLocalDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 公告相关api
 * @author GLaDOS
 * @date 2023/3/24 22:00
 */
@Api(value = "公告相关api", tags = "公告相关api")
@Slf4j
@RestController
public class BroadcastController {

    @Autowired
    private BroadcastService broadcastService;

    /**
     * 发表公告
     * 仅管理员能调用该api
     * @param broadcast 公告
     * @return RR
     */
    @ApiOperation("发表公告")
    @RequestMapping(value = "/broadcast", method = RequestMethod.PUT)
    public RestResponse<Broadcast> postBroadcast(@RequestBody Broadcast broadcast){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        IsAuth.init(authThreadLocal).or("900").start();
        if (broadcast.getPubilsherId() == null)
            broadcast.setPubilsherId(authThreadLocal.get().getId());
        return broadcastService.postBroadcast(broadcast);
    }

    /**
     * 更新公告
     * 仅管理员能调用该api
     * @param updateBroadcastDto 更新信息
     * @return RR
     */
    @ApiOperation("更新公告")
    @RequestMapping(value = "/broadcast", method = RequestMethod.POST)
    public RestResponse<Broadcast> updateBroadcast(@RequestBody UpdateBroadcastDto updateBroadcastDto){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        IsAuth.init(authThreadLocal).or("900").start();
        return broadcastService.updateBroadcast(updateBroadcastDto);
    }

    /**
     * 查询公告
     * 除super外其他人只能查询对应correspond的权限
     * @param pageNo 页数
     * @param queryBroadcastDto 查询条件
     * @return PR
     */
    @ApiOperation("查询公告")
    @RequestMapping(value = "/broadcast/query/{pageNo}", method = RequestMethod.GET)
    public PageResult<Broadcast> queryBroadcast(@PathVariable Long pageNo, QueryBroadcastDto queryBroadcastDto) {
        PageParams pageParams = new PageParams(pageNo);
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        String userAuth = authThreadLocal.get().getAuth();
        return broadcastService.queryBroadcast(pageParams, queryBroadcastDto, userAuth);
    }

    /**
     * 根据id删除公告信息
     * 仅对应的publisher_id能删除
     * @param bid id
     * @return RR
     */
    @ApiOperation("根据id删除公告信息")
    @RequestMapping(value = "/broadcast/{bid}", method = RequestMethod.DELETE)
    public RestResponse<Broadcast> deleteBroadcastById(@PathVariable Integer bid){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        IsAuth.init(authThreadLocal).or("900").start();
        return broadcastService.deleteBroadcastById(bid, authThreadLocal.get().getId());
    }
}
