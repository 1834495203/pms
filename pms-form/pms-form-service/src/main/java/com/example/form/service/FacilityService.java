package com.example.form.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.form.model.dto.QueryFacilityDto;
import com.example.form.model.dto.UpdateFacilityDto;
import com.example.form.model.po.Facility;
import com.example.model.PageParams;
import com.example.model.PageResult;
import com.example.model.RestResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GLaDOS
 * @since 2023-03-21
 */
public interface FacilityService extends IService<Facility> {

    /**
     * 添加设施信息
     * @param facility 设施信息
     * @return RR
     */
    RestResponse<Facility> insertFacilityInfo(Facility facility);

    /**
     * 更新设施信息
     * @param updateFacilityDto 设施更新的内容
     * @param userAuth 用户权限
     * @return RR
     */
    RestResponse<Facility> updateFacilityInfo(UpdateFacilityDto updateFacilityDto,
                                              String userAuth);

    /**
     * 删除设备信息
     * @param fid id
     * @return RR
     */
    RestResponse<Facility> deleteFacilityById(int fid);

    /**
     * 根据id查询设备信息
     * @param fid id
     * @return RR
     */
    RestResponse<Facility> queryFacilityById(int fid);

    /**
     * 查询设备信息
     * @param queryFacilityDto 查询信息dto
     * @return PR
     */
    PageResult<Facility> queryFacilityPre(PageParams pageParams, QueryFacilityDto queryFacilityDto);
}
