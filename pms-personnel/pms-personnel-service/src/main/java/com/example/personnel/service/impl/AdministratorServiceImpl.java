package com.example.personnel.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.exception.Error;
import com.example.exception.PMSException;
import com.example.model.RestResponse;
import com.example.model.Valid;
import com.example.personnel.mapper.AdministratorMapper;
import com.example.personnel.model.po.Administrator;
import com.example.personnel.service.AdministratorService;
import com.example.personnel.utils.UploadFiles;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;


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

    @Override
    public RestResponse<?> upLoadUserProfile(String filename,
                                             String localFilePath,
                                             Integer id){

        Administrator admin = administratorMapper.selectById(id);
        return uploadFiles.uploadFile(filename, localFilePath, administratorMapper, bucket, admin, minioClient);
    }
}
