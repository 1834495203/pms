package com.example.form.controller;

import com.example.form.config.AuthThreadLocal;
import com.example.form.model.dto.QueryFacilityDto;
import com.example.form.model.dto.UpdateFacilityDto;
import com.example.form.model.dto.UserThreadLocalDto;
import com.example.form.model.po.Facility;
import com.example.form.service.FacilityService;
import com.example.form.util.IsAuth;
import com.example.model.PageParams;
import com.example.model.PageResult;
import com.example.model.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 设施管理接口
 * @author GLaDOS
 * @date 2023/3/23 21:31
 */
@Api(value = "设施管理接口", tags = "设施管理接口")
@Slf4j
@RestController
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

    /**
     * 添加设施信息
     * 仅管理员能调用此api
     * @param facility 设施信息
     * @return RR
     */
    @ApiOperation("添加设施信息")
    @RequestMapping(value = "/facility", method = RequestMethod.PUT)
    public RestResponse<Facility> putFacilityInfo(@RequestBody Facility facility){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        IsAuth.init(authThreadLocal).or("90090").start();
        return facilityService.insertFacilityInfo(facility);
    }

    /**
     * 更新设备信息
     * 仅管理员与设备管理员能调用此api
     * @param updateFacilityDto 设备更新信息
     * @return RR
     */
    @ApiOperation("更新设备信息")
    @RequestMapping(value = "/facility", method = RequestMethod.POST)
    public RestResponse<Facility> updateFacility(@RequestBody UpdateFacilityDto updateFacilityDto){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        IsAuth.init(authThreadLocal).or("90090").or("90091").start();
        return facilityService.updateFacilityInfo(updateFacilityDto, authThreadLocal.get().getAuth());
    }

    /**
     * 根据id删除设备信息
     * 仅设备管理员能调用此api
     * @param fid id
     * @return RR
     */
    @ApiOperation("根据id删除设施信息")
    @RequestMapping(value = "/facility/{fid}", method = RequestMethod.DELETE)
    public RestResponse<Facility> deleteFacilityById(@PathVariable Integer fid){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        IsAuth.init(authThreadLocal).or("90091").start();
        return facilityService.deleteFacilityById(fid);
    }

    /**
     * 根据id查询设备信息
     * 没有权限设置
     * @param fid id
     * @return RR
     */
    @ApiOperation("根据id查询设备信息")
    @RequestMapping(value = "/facility/{fid}", method = RequestMethod.GET)
    public RestResponse<Facility> getFacilityById(@PathVariable Integer fid){
        return facilityService.queryFacilityById(fid);
    }

    /**
     * 根据条件查询设施的信息
     * 公用api
     * @param queryFacilityDto 查询设施的条件
     * @return PR
     */
    @ApiOperation("根据条件查询设施的信息")
    @RequestMapping(value = "/facility/query/{pageNo}", method = RequestMethod.GET)
    public PageResult<Facility> queryFacility(QueryFacilityDto queryFacilityDto, @PathVariable Long pageNo){
        PageParams pageParams = new PageParams(pageNo);
        return facilityService.queryFacilityPre(pageParams, queryFacilityDto);
    }
}
