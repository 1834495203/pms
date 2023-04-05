package com.example.form.controller;

import com.example.config.AuthThreadLocal;
import com.example.exception.PMSException;
import com.example.form.model.po.Pict;
import com.example.form.service.PictService;
import com.example.model.RestResponse;
import com.example.model.UserThreadLocalDto;
import com.example.utils.IsAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author GLaDOS
 * @date 2023/4/2 23:21
 */
@Slf4j
@Api(value = "文件接口", tags = "文件接口")
@RestController
public class PictController {

    @Autowired
    private PictService pictService;

    /**
     * 文件上传api
     * @param file 文件
     * @return RR
     * @throws IOException 文件io
     */
    @ApiOperation("文件上传api")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public RestResponse<Pict> uploadPict(@RequestPart("file") MultipartFile file, HttpServletRequest request) throws IOException {
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        IsAuth.init(authThreadLocal).or("910").or("900").start();

        //保存在本地
        File temp = File.createTempFile("minio", ".temp");
        file.transferTo(temp);
        //获取文件路径
        String path = temp.getAbsolutePath();
        log.info("上传的文件路径为: {}", path);

        String type = request.getHeader("type");
        log.info("从headers传入的的值为： {}", type);

        //type表示文件所属信息
        if (type == null)
            throw new PMSException("没有携带指定header");
        RestResponse<Pict> pictRestResponse = pictService.uploadPict(file.getOriginalFilename(), path, type);
        if (temp.delete()) log.info("临时文件: {} 删除成功", file.getOriginalFilename());
        return pictRestResponse;
    }

    /**
     * 文件删除
     * @param id 文件id
     * @return RR
     */
    @ApiOperation("文件删除api")
    @RequestMapping(value = "/upload/{id}", method = RequestMethod.DELETE)
    public RestResponse<Pict> deletePict(@PathVariable("id") Integer id){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        IsAuth.init(authThreadLocal).or("910").or("900").start();

        return pictService.deletePict(id);
    }

    /**
     * 根据id查询pict信息
     * @param id 文件id
     * @return RR
     */
    @RequestMapping(value = "/upload/{id}", method = RequestMethod.GET)
    public RestResponse<Pict> queryById(@PathVariable Integer id){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        IsAuth.init(authThreadLocal).or("910").or("900").start();
        return pictService.queryById(id);
    }
}
