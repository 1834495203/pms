package com.example.form.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.exception.Error;
import com.example.exception.PMSException;
import com.example.model.RestResponse;
import com.example.model.Valid;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author GLaDOS
 * @date 2023/4/1 18:22
 */
@Slf4j
@Component
public class UploadFiles {

    /**
     * 根据拓展名获取mimeType
     * @param ex 文件后缀
     * @return 文件类型
     */
    private static String getMimeType(String ex){
        if (ex == null) ex = "";
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(ex);
        if (extensionMatch != null)
            return extensionMatch.getMimeType();
        //为空则返回通用类型 字节流
        return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }

    /**
     * 获取文件的md5
     * @param file 文件
     * @return md5
     */
    private static String getFileMd5(File file){
        try {
            FileInputStream fis = new FileInputStream(file);
            return DigestUtil.md5Hex(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("文件名转换为md5失败-------->"+e.getMessage());
            return null;
        }
    }

    public String uploadFile2Minio(String filename, String localFilePath, String bucket, MinioClient minioClient){
        //获取后缀
        String ex = filename.substring(filename.lastIndexOf("."));
        //得到mimeType
        String mimeType = UploadFiles.getMimeType(ex);
        //获取文件的md5
        String fileMd5 = UploadFiles.getFileMd5(new File(localFilePath));
        //文件在minio中保存的地址
        String objectName = DateUtil.format(new Date(), "yyyy/MM/dd") + "/" + fileMd5 + ex;

        //将文件上传至minio
        try{
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .filename(localFilePath)
                            .contentType(mimeType)
                            .build()
            );
            return objectName;
        }catch (Exception e){
            e.printStackTrace();
            throw new PMSException("上传失败!", Error.UPLOAD_FILE_FAILED);
        }
    }

    /**
     * 上传用户头像
     * @param filename 文件名
     * @param localFilePath 文件地址
     * @param mapper 用户对应的mapper对象
     * @param bucket 桶
     * @param instance 从数据库获取的用户类实例
     * @param <T> mapper
     * @param <K> 用户类
     * @return RR
     */
    public <T extends BaseMapper<K>, K> RestResponse<?> uploadFile(String filename,
                                                                   String localFilePath,
                                                                   T mapper,
                                                                   String bucket,
                                                                   K instance,
                                                                   MinioClient minioClient){
        if (instance == null)
            return RestResponse.validFail("没有对象!", Error.UNAUTHORIZED);
        UploadFiles uploadFiles = new UploadFiles();

        try {
            Field profile = instance.getClass().getDeclaredField("profile");
            profile.setAccessible(true);
            //先判断用户是否已经有头像
            String path;
            if ((path = (String) profile.get(instance)) != null){
                //先删除用户当前头像
                minioClient.removeObject(
                        RemoveObjectArgs.builder()
                                .bucket(bucket)
                                .object(path)
                                .build()
                );
            }

            //上传头像
            String objectName = uploadFiles.uploadFile2Minio(filename, localFilePath, bucket, minioClient);

            //将文件路径上传至db
            profile.set(instance, objectName);
            if (mapper.updateById(instance) == 1)
                return RestResponse.success("success", objectName, Valid.UPLOAD_FILE_SUCCESS);
            return RestResponse.validFail("上传失败!", Error.UPLOAD_FILE_FAILED);
        }catch (Exception e){
            e.printStackTrace();
            throw new PMSException("上传失败!", Error.UPLOAD_FILE_FAILED);
        }
    }
}
