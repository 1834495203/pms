package com.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.auth.po.Administrator;

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
}
