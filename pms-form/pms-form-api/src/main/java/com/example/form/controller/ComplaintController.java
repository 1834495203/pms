package com.example.form.controller;

import com.example.config.AuthThreadLocal;
import com.example.form.advice.WithAuth;
import com.example.form.model.dto.PostComplaintDto;
import com.example.form.model.dto.QueryComplaintDto;
import com.example.form.model.dto.ResultComplaintDto;
import com.example.form.model.dto.UpdateComplaintDto;
import com.example.form.model.po.Complaint;
import com.example.form.service.ComplaintService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Api(value = "投诉接口", tags = "投诉接口")
@Slf4j
@RestController
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    /**
     * 插入投诉信息
     * 仅业主能发送投诉(插入), 仅前台和管理员能接收投诉(获取)
     * @param complaint 投诉消息
     * @return RR
     */
    @ApiOperation("业主发送投诉")
    @RequestMapping(value = "/complaint", method = RequestMethod.PUT)
    public RestResponse<Complaint> postComplaint(@RequestBody PostComplaintDto complaint){
        complaint.setCreateTime(LocalDateTime.now());
        complaint.setCreateDate(LocalDate.now());
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
    @Deprecated
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
     * 都能调用此api
     * @param queryComplaintDto 查询条件
     * @return 分页数据
     */
    @WithAuth(orAuth = {"900", "910"})
    @ApiOperation("根据指定条件返回投诉信息")
    @RequestMapping(value = "/complaint/query/{pageNo}", method = RequestMethod.POST)
    public PageResult<ResultComplaintDto> getComplaint(@RequestBody QueryComplaintDto queryComplaintDto,
                                                       @PathVariable Long pageNo){
        PageParams pageParams = new PageParams(pageNo);
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        UserThreadLocalDto userThreadLocalDto = authThreadLocal.get();
        IsAuth.init(userThreadLocalDto).or("900").or("910").start();
        //TODO 实现条件查询投诉单的信息的service
        String auth = userThreadLocalDto.getAuth();
        return complaintService.selectComplaint(pageParams, queryComplaintDto, auth);
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

    /**
     * 修改投诉中的相关信息
     * @param updateComplaintDto 修改的信息
     * @return RR
     */
    @ApiOperation("修改投诉信息中的内容")
    @RequestMapping(value = "/complaint", method = RequestMethod.POST)
    public RestResponse<Complaint> updateComplaint(@RequestBody UpdateComplaintDto updateComplaintDto){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        //910为业主, 900为管理员
        IsAuth.init(authThreadLocal).or("900").or("910").start();
        return complaintService.updateComplaint(authThreadLocal.get().getAuth(), updateComplaintDto);
    }

    @ApiOperation("测试threadLocal保存与获取用户的auth")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void testThreadLocal(){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        UserThreadLocalDto user = authThreadLocal.get();
        log.info("测试threadLocal保存与获取用户的auth为: {}", user.getAuth());
    }
}
