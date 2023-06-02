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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 楼栋管理
 * @author GLaDOS
 * @date 2023/4/9 22:35
 */
@Slf4j
@Api(value = "楼栋管理", tags = "楼栋管理")
@RestController
public class BuildingController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private InformationService informationService;

    /**
     * 根据栋id获取楼栋信息
     * @param id 栋id
     * @return RR
     */
    @ApiOperation("根据id获取楼栋信息")
    @RequestMapping(value = "/building/{id}", method = RequestMethod.GET)
    public RestResponse<ResultHouseDto> getBuildingInfoById(@PathVariable Integer id, HttpServletRequest request){
        String type = request.getHeader("type");
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        IsAuth.init(authThreadLocal).or("900").start();
        return houseService.getBuildingInfoById(id, type);
    }

    @ApiOperation("根据门牌号获取房产信息")
    @RequestMapping(value = "/building/door/{doorplate}", method = RequestMethod.GET)
    public Information getHouseInfoByDoorPlate(@PathVariable String doorplate){
        return houseService.getHouseInfoByDoorPlate(doorplate);
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

    @ApiOperation("将业主信息与房屋信息绑定")
    @RequestMapping(value = "/building/info", method = RequestMethod.POST)
    public Information bindHouseInfo2Prop(@RequestParam("pid") Integer pid, @RequestParam("hid") Integer hid){
        return informationService.bindHouseInfo2Prop(pid, hid);
    }

    @RequestMapping(value = "/building/address/info", method = RequestMethod.POST)
    public RestResponse<ResultHouseDto> selectAssignedAddress(@RequestParam("address") String address){
        return houseService.selectAssignedAddress(address);
    }
}
