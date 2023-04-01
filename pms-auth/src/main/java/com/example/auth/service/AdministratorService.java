package com.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.auth.po.Administrator;
import com.example.model.RestResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GLaDOS
 * @since 2023-03-20
 */
public interface AdministratorService extends IService<Administrator> {

    /**
     * 管理员登录
     * @param administrator 管理员po
     * @return token
     */
    String loginForAdmin(Administrator administrator);

    /**
     * 管理员注册
     * @param administrator 管理员po
     * @return boolean
     */
    boolean registerForAdmin(Administrator administrator);

    /**
     * 获取当前用户的权限
     * @param id 用户id
     * @param auth 用户权限
     * @return RR
     */
    RestResponse<Administrator> getCurrentAdminInfo(Integer id, String auth);
}
