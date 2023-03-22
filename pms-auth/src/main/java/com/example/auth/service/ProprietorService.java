package com.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.auth.po.Proprietor;

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
}
