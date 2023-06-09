package com.example.auth.controller;

import com.example.auth.dto.UpdatePropDto;
import com.example.auth.po.Proprietor;
import com.example.auth.service.AdministratorService;
import com.example.auth.service.ProprietorService;
import com.example.config.AuthThreadLocal;
import com.example.exception.Error;
import com.example.exception.PMSException;
import com.example.model.RestResponse;
import com.example.model.UserThreadLocalDto;
import com.example.utils.IsAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author GLaDOS
 * @date 2023/4/8 23:57
 */
@Api(value = "用户信息修改", tags = "用户信息修改")
@Slf4j
@RestController
public class AlterUserController {

    @Autowired
    private ProprietorService proprietorService;

    @Autowired
    private AdministratorService administratorService;

    /**
     * 上传用户头像
     * @param file 头像
     * @return RR
     * @throws IOException 文件
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public RestResponse<?> uploadUserProfile(@RequestPart("file") MultipartFile file) throws IOException {
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        if (authThreadLocal.get() == null)
            throw new PMSException("没有对象!", Error.UNAUTHORIZED);
        String auth = authThreadLocal.get().getAuth();
        Integer id = authThreadLocal.get().getId();

        //保存在本地
        File temp = File.createTempFile("minio", ".temp");
        file.transferTo(temp);
        //获取文件路径
        String path = temp.getAbsolutePath();
        log.info("上传的文件路径为: {}", path);

        RestResponse<?> r = RestResponse.validFail("上传失败!", Error.UPLOAD_FILE_FAILED);
        //判断用户身份
        if (auth.contains("910"))
            r = proprietorService.upLoadUserProfile(file.getOriginalFilename(), path, id);
        else if (auth.contains("900"))
            r = administratorService.upLoadUserProfile(file.getOriginalFilename(), path, id);
        boolean delete = temp.delete();
        if (delete) log.info("临时文件: {} 删除成功", file.getOriginalFilename());
        return r;
    }

    @RequestMapping(value = "/alter/info/prop", method = RequestMethod.POST)
    public RestResponse<Proprietor> updatePropInfo(@RequestBody UpdatePropDto updatePropDto){
        ThreadLocal<UserThreadLocalDto> authThreadLocal = AuthThreadLocal.getAuthThreadLocal();
        IsAuth.init(authThreadLocal).or("900").start();
        log.info(updatePropDto.toString());
        return proprietorService.updateProprietor(updatePropDto);
    }
}
