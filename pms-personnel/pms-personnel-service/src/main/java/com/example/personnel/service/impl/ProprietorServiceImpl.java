package com.example.personnel.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.exception.Error;
import com.example.exception.PMSException;
import com.example.model.RestResponse;
import com.example.model.Valid;
import com.example.personnel.mapper.ProprietorMapper;
import com.example.personnel.model.po.Proprietor;
import com.example.personnel.service.ProprietorService;
import com.example.personnel.utils.UploadFiles;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GLaDOS
 */
@Slf4j
@Service
public class ProprietorServiceImpl extends ServiceImpl<ProprietorMapper, Proprietor> implements ProprietorService {

    @Autowired
    private ProprietorMapper proprietorMapper;

    @Value("${minio.bucket.files}")
    private String bucket;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private UploadFiles uploadFiles;

    @Override
    public RestResponse<?> upLoadUserProfile(String filename,
                                             String localFilePath,
                                             Integer id) {
        Proprietor proprietor = proprietorMapper.selectById(id);
        return uploadFiles.uploadFile(filename, localFilePath, proprietorMapper, bucket, proprietor, minioClient);
    }
}
