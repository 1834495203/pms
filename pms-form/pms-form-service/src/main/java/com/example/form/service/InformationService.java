package com.example.form.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
public interface InformationService extends IService<Information> {

    /**
     * 根据房产地址id查询房产信息
     * @param id hid
     * @return RR
     */
    RestResponse<Information> selectInfoByHid(Integer id);

    /**
     * 绑定业主与房产信息
     * @param pid 业主id
     * @param hid 房产id
     * @return Information
     */
    Information bindHouseInfo2Prop(Integer pid, Integer hid);
}
