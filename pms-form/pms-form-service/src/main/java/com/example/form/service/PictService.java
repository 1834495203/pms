package com.example.form.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.form.model.po.Pict;
import com.example.model.RestResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GLaDOS
 * @since 2023-04-02
 */
public interface PictService extends IService<Pict> {

    /**
     * 上传文件
     * @param filename 文件名
     * @param localFilePath 保存在本地的文件路径
     * @param type 类型
     * @return RR
     */
    RestResponse<Pict> uploadPict(String filename, String localFilePath, String type);

    /**
     * 删除文件
     * @param id 文件id
     * @return RR
     */
    RestResponse<Pict> deletePict(Integer id);

}
