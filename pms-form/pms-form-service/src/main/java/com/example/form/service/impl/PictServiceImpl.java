package com.example.form.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.exception.Error;
import com.example.exception.PMSException;
import com.example.form.mapper.PictMapper;
import com.example.form.model.po.Pict;
import com.example.form.service.PictService;
import com.example.form.util.UploadFiles;
import com.example.model.RestResponse;
import com.example.model.Valid;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GLaDOS
 */
@Slf4j
@Service
public class PictServiceImpl extends ServiceImpl<PictMapper, Pict> implements PictService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket.files}")
    private String bucket;

    @Autowired
    private UploadFiles uploadFiles;

    @Autowired
    private PictMapper pictMapper;

    private static final String FILE_URL = "http://api.mahiro.com/file/mediafiles/";

    @Override
    public RestResponse<Pict> uploadPict(String filename, String localFilePath, String type) {
        Pict pict = new Pict();
        pict.setName(filename);
        pict.setType(type);
        //上传至minio
        String path = uploadFiles.uploadFile2Minio(filename, localFilePath, bucket, minioClient);
        pict.setObjectName(FILE_URL+path);

        if (pictMapper.insert(pict) == 1)
            return RestResponse.success(pict, "上传成功", Valid.UPLOAD_FILE_SUCCESS);
        return RestResponse.validFail("上传失败", Error.UPLOAD_FILE_FAILED);
    }

    @Override
    public RestResponse<Pict> deletePict(Integer id) {
        Pict pict = pictMapper.selectById(id);
        if (pict == null)
            return RestResponse.validFail("没有对象！", Error.DATABASE_SELECT_FAILED);
        try{
            pict.setObjectName(pict.getObjectName().replaceAll(FILE_URL, ""));
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(pict.getObjectName())
                            .build()
            );
        }catch (Exception e){
            e.printStackTrace();
            throw new PMSException("删除文件失败", Error.DELETE_FILE_FAILED);
        }
        if (pictMapper.deleteById(pict.getPid()) == 1)
            return RestResponse.success(pict, "删除成功", Valid.DATABASE_DELETE_SUCCESS);
        return RestResponse.validFail("删除失败", Error.DATABASE_DELETE_FAILED);
    }

    @Override
    public RestResponse<Pict> queryById(Integer id) {
        Pict pict = pictMapper.selectById(id);
        if (pict == null) return RestResponse.validFail("没有对象！", Error.DATABASE_SELECT_FAILED);
        return RestResponse.success(pict, "获取成功", Valid.DATABASE_SELECT_SUCCESS);
    }


}
