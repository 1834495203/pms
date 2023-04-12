package com.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.auth.dto.QueryPersonnelDto;
import com.example.auth.dto.ResultUserBaseInfo;
import com.example.auth.po.Proprietor;
import com.example.model.PageParams;
import com.example.model.PageResult;
import com.example.model.RestResponse;

import java.util.List;

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

    /**
     * 获取全部业主信息
     * @return RR
     */
    PageResult<Proprietor> getListProprietor(PageParams pageParams, QueryPersonnelDto queryPersonnelDto);
}
