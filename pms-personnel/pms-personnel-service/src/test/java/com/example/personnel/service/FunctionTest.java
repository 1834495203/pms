package com.example.personnel.service;

import cn.hutool.core.date.DateUtil;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.http.Method;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author GLaDOS
 * @date 2023/4/1 14:49
 */
@SpringBootTest
public class FunctionTest {

    @Autowired
    private MinioClient minioClient;

    @Test
    void uploadTest() throws Exception{
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket("testbuckets")
                        .filename("C:\\Users\\asus\\Desktop\\废柴狐阿桔\\cat.jpg")
                        .object("/a/cat.jpg")
                        .build()
        );
    }

    @Test
    void getTest() throws Exception{
        System.out.println(minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket("testbuckets")
                        .expiry(1, TimeUnit.MINUTES)
                        .object("by your side.mp3")
                        .method(Method.GET)
                        .build()
        ));
    }

    @Test
    void testUtil(){
        String path = DateUtil.format(new Date(), "yyyy/MM/dd");
        System.out.println(path);
    }
}
