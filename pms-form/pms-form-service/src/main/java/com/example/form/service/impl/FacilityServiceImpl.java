package com.example.form.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.exception.Error;
import com.example.form.mapper.FacilityMapper;
import com.example.form.model.dto.UpdateFacilityDto;
import com.example.form.model.po.Facility;
import com.example.form.service.FacilityService;
import com.example.model.RestResponse;
import com.example.model.Valid;
import com.example.utils.HasAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GLaDOS
 */
@Slf4j
@Service
public class FacilityServiceImpl extends ServiceImpl<FacilityMapper, Facility> implements FacilityService {

    @Autowired
    private FacilityMapper facilityMapper;

    @Override
    public RestResponse<Facility> insertFacilityInfo(Facility facility) {
        if (facility.getCreateDate() == null)
            facility.setCreateDate(LocalDateTime.now());
        //根据时间生成唯一uuid
        facility.setSoleId(UUID.fromString(facility.getCreateDate().toString()).toString());
        if (facilityMapper.insert(facility) == 1)
            return RestResponse.success(facility, "添加成功", Valid.DATABASE_INSERT_SUCCESS);
        return RestResponse.validFail(facility, "添加失败", Error.DATABASE_INSERT_FAILED);
    }

    @Override
    public RestResponse<Facility> updateFacilityInfo(UpdateFacilityDto updateFacilityDto,
                                                     String userAuth) {
        Facility facility = facilityMapper.selectById(updateFacilityDto.getFid());
        if (facility == null)
            return RestResponse.validFail("没有该对象", Error.DATABASE_SELECT_FAILED);
        HasAuth<UpdateFacilityDto> updateFacilityDtoHasAuth = new HasAuth<>(userAuth, updateFacilityDto, UpdateFacilityDto.class);
        //返回鉴权后的类
        UpdateFacilityDto dtoAfterAuth = updateFacilityDtoHasAuth.afterAuth();
        BeanUtil.copyProperties(dtoAfterAuth, facility, CopyOptions.create().ignoreNullValue());
        if (facilityMapper.updateById(facility) == 1)
            return RestResponse.success(facility, "更新成功", Valid.DATABASE_UPDATE_SUCCESS);
        return RestResponse.validFail("更新失败", Error.DATABASE_UPDATE_FAILED);
    }

    @Override
    public RestResponse<Facility> deleteFacilityById(int fid) {
        Facility facility = facilityMapper.selectById(fid);
        if (facility == null)
            return RestResponse.validFail("没有该对象", Error.DATABASE_SELECT_FAILED);
        if (facilityMapper.deleteById(fid) == 1)
            return RestResponse.success(facility, "删除成功", Valid.DATABASE_DELETE_SUCCESS);
        return RestResponse.validFail("删除失败", Error.DATABASE_DELETE_FAILED);
    }
}
