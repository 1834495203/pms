package com.example.form.controller;

import com.example.form.config.AuthThreadLocal;
import com.example.form.model.dto.PostComplaintDto;
import com.example.form.model.dto.QueryComplaintDto;
import com.example.form.model.dto.UserThreadLocalDto;
import com.example.form.model.po.Complaint;
import com.example.form.service.ComplaintService;
import com.example.form.util.IsAuth;
import com.example.model.PageParams;
import com.example.model.PageResult;
import com.example.model.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.function.Predicate;

@Api(value = "投诉接口", tags = "投诉接口")
@Slf4j
@RestController
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    /**
     * 仅业主能发送投诉(插入), 仅前台和管理员能接收投诉(获取)
     * @param complaint 投诉消息
     * @return RR
     */
    @ApiOperation("业主发送投诉")
    @RequestMapping(value = "/complaint", method = RequestMethod.POST)
    public RestResponse<Complaint> postComplaint(@RequestBody PostComplaintDto complaint){
        complaint.setCreateTime(LocalDateTime.now());
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        //910为业主的前缀
        IsAuth.init(authThreadLocal).or("910").start();
        //将当前业主id写入
        complaint.setPubilsherId(authThreadLocal.get().getId());
        return complaintService.postComplaint(complaint);
    }

    /**
     * 根据业主id获取用户所发送的投诉信息
     * 仅业主能调用该api
     * @param pageParams 分页信息
     * @return 分页数据
     */
    @ApiOperation("根据当前用户id获取其发送的投诉信息, 返回分页信息")
    @RequestMapping(value = "/complaint", method = RequestMethod.GET)
    public PageResult<Complaint> getComplaintByUserId(PageParams pageParams){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        //910为业主前缀
        IsAuth.init(authThreadLocal).or("910").start();
        return complaintService.getComplaintByUserId(pageParams, authThreadLocal.get().getId());
    }

    /**
     * 条件查询投诉单的信息
     * 仅前台能调用此api
     * @param queryComplaintDto 查询条件
     * @return 分页数据
     */
    @ApiOperation("根据指定条件返回投诉信息")
    @RequestMapping(value = "/complaint/query", method = RequestMethod.GET)
    public PageResult<Complaint> getComplaint(@RequestBody QueryComplaintDto queryComplaintDto, PageParams pageParams){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        UserThreadLocalDto userThreadLocalDto = authThreadLocal.get();
        IsAuth.init(userThreadLocalDto).or("90093").start();
        if (queryComplaintDto.getCreateTime() != null && queryComplaintDto.getQueryTime() == null)
            queryComplaintDto.setQueryTime(0);
        //TODO 实现条件查询投诉单的信息的service
        return complaintService.selectComplaint(pageParams, queryComplaintDto);
    }

    /**
     * 根据cid删除投诉信息
     * 仅对应的业主与前台能调用此api
     * @param cid 投诉cid
     * @return RR
     */
    @ApiOperation("根据cid删除投诉信息")
    @RequestMapping(value = "/complaint/{cid}", method = RequestMethod.DELETE)
    public RestResponse<Complaint> deleteComplaintByCid(@PathVariable Integer cid){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        //910为业主, 90093为前台
        IsAuth.init(authThreadLocal).or("910").or("90093").start();
        return complaintService.deleteComplaintByCid(cid, authThreadLocal.get().getAuth(),
                authThreadLocal.get().getId());
    }

    @ApiOperation("测设threadLocal保存与获取用户的auth")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void testThreadLocal(){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        UserThreadLocalDto user = authThreadLocal.get();
        log.info(user.getAuth());
    }
}
