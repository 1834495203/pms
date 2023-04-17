package com.example.form.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.form.model.dto.ResultHouseDto;
import com.example.form.model.po.House;
import com.example.form.model.po.Information;
import com.example.model.RestResponse;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GLaDOS
 * @since 2023-04-09
 */
public interface HouseService extends IService<House> {

    /**
     * 根据id获取楼栋信息
     * @param id 楼栋id
     * @return RR
     */
    RestResponse<ResultHouseDto> getBuildingInfoById(Integer id, String type);

    /**
     * 添加楼栋信息
     * @param house po
     * @return RR
     */
    RestResponse<House> appendBuildingInfo(House house);

    /**
     * 删除楼栋信息
     * @param resultHouseDto dto
     * @return RR
     */
    RestResponse<ResultHouseDto> deleteBuildingInfo(ResultHouseDto resultHouseDto);

    /**
     * 获取指定地址
     * @param address 地址
     * @return RR
     */
    RestResponse<ResultHouseDto> selectAssignedAddress(String address);
}
