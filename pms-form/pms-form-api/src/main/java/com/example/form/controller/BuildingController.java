package com.example.form.controller;

import com.example.config.AuthThreadLocal;
import com.example.form.model.dto.ResultHouseDto;
import com.example.form.model.po.House;
import com.example.form.model.po.Information;
import com.example.form.service.HouseService;
import com.example.form.service.InformationService;
import com.example.form.util.IsAuth;
import com.example.model.RestResponse;
import com.example.model.UserThreadLocalDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 楼栋管理
 * @author GLaDOS
 * @date 2023/4/9 22:35
 */
@Api(value = "楼栋管理", tags = "楼栋管理")
@RestController
public class BuildingController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private InformationService informationService;

    /**
     * 根据id获取楼栋信息
     * @param id id
     * @return RR
     */
    @ApiOperation("根据id获取楼栋信息")
    @RequestMapping(value = "/building/{id}", method = RequestMethod.GET)
    public RestResponse<ResultHouseDto> getBuildingInfoById(@PathVariable Integer id){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        IsAuth.init(authThreadLocal).or("900").start();
        return houseService.getBuildingInfoById(id);
    }

    @ApiOperation("添加楼栋节点信息")
    @RequestMapping(value = "/building", method = RequestMethod.PUT)
    public RestResponse<House> appendBuildingInfo(@RequestBody House house){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        IsAuth.init(authThreadLocal).or("900").start();
        return houseService.appendBuildingInfo(house);
    }

    @ApiOperation("删除楼栋节点信息")
    @RequestMapping(value = "/building", method = RequestMethod.POST)
    public RestResponse<ResultHouseDto> deleteBuildingInfo(@RequestBody ResultHouseDto resultHouseDto){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        IsAuth.init(authThreadLocal).or("900").start();
        return houseService.deleteBuildingInfo(resultHouseDto);
    }

    @ApiOperation("根据hid查询房产信息")
    @RequestMapping(value = "/building/info/{id}", method = RequestMethod.GET)
    public RestResponse<Information> selectHouseInfoByHid(@PathVariable Integer id){
        return informationService.selectInfoByHid(id);
    }
}
