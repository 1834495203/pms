package com.example.form.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.form.model.po.Information;
import com.example.model.RestResponse;

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
}
