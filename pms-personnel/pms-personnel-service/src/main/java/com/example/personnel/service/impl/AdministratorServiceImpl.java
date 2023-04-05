package com.example.personnel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.exception.Error;
import com.example.model.RestResponse;
import com.example.model.Valid;
import com.example.personnel.mapper.AdministratorMapper;
import com.example.personnel.model.dto.ResultUserBaseInfo;
import com.example.personnel.model.po.Administrator;
import com.example.personnel.service.AdministratorService;
import com.example.personnel.utils.UploadFiles;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GLaDOS
 */
@Slf4j
@Service
public class AdministratorServiceImpl extends ServiceImpl<AdministratorMapper, Administrator> implements AdministratorService {

    @Autowired
    private AdministratorMapper administratorMapper;

    @Value("${minio.bucket.files}")
    private String bucket;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private UploadFiles uploadFiles;

    @Transactional
    @Override
    public RestResponse<?> upLoadUserProfile(String filename,
                                             String localFilePath,
                                             Integer id){

        Administrator admin = administratorMapper.selectById(id);
        return uploadFiles.uploadFile(filename, localFilePath, administratorMapper, bucket, admin, minioClient);
    }

    @Override
    public ResultUserBaseInfo getBaseInfoById(Integer id) {
        Administrator administrator = administratorMapper.selectById(id);
        if (administrator == null)
            return null;
        ResultUserBaseInfo resultUserBaseInfo = new ResultUserBaseInfo();
        BeanUtil.copyProperties(administrator, resultUserBaseInfo, CopyOptions.create().ignoreNullValue());
        return resultUserBaseInfo;
    }
}
