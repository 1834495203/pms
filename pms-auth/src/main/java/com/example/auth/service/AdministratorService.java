package com.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.auth.po.Administrator;
import com.example.auth.dto.ResultUserBaseInfo;
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

    /**
     * 用户上传图片
     * @param filename 图片名字
     * @param localFilePath 本地路径
     * @param id 用户id
     * @return RR
     */
    RestResponse<?> upLoadUserProfile(String filename, String localFilePath, Integer id);

    /**
     * 通过id获取用户基本信息
     * @param id id
     * @return ResultUserBaseInfo
     */
    ResultUserBaseInfo getBaseInfoById(Integer id);
}
