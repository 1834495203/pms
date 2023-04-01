package com.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.auth.po.Proprietor;
import com.example.model.RestResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GLaDOS
 * @since 2023-03-20
 */
public interface ProprietorService extends IService<Proprietor> {

    /**
     * 业主登录
     * @param proprietor 业主po
     * @return token
     */
    String loginForProprietor(Proprietor proprietor);

    /**
     * 业主注册
     * @param proprietor 业主po
     * @return boolean
     */
    boolean registerForProprietor(Proprietor proprietor);

    /**
     * 根据id获取用户的基本信息
     * @param id 用户id
     * @param auth 用户权限
     * @return RR
     */
    RestResponse<Proprietor> getCurrentProprietor(Integer id, String auth);
}
