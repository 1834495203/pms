package com.example.personnel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.model.RestResponse;
import com.example.personnel.model.dto.ResultUserBaseInfo;
import com.example.personnel.model.po.Proprietor;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GLaDOS
 * @since 2023-04-01
 */
public interface ProprietorService extends IService<Proprietor> {

    /**
     * 用户上传图片
     * @param filename 图片名字
     * @param localFilePath 本地路径
     * @param id 用户id
     * @return RR
     */
    RestResponse<?> upLoadUserProfile(String filename, String localFilePath, Integer id);

    /**
     * 通过id搜索用户基本信息
     * @param id 用户id
     * @return ResultUserBaseInfo
     */
    ResultUserBaseInfo getUserBaseInfo(Integer id);
}
