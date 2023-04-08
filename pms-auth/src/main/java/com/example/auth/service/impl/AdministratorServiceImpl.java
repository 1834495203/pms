package com.example.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.mapper.AdministratorMapper;
import com.example.auth.po.Administrator;
import com.example.auth.dto.ResultUserBaseInfo;
import com.example.auth.service.AdministratorService;
import com.example.auth.config.JwtConfig;
import com.example.auth.util.UploadFiles;
import com.example.exception.Error;
import com.example.exception.PMSException;
import com.example.model.RestResponse;
import com.example.model.Valid;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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

    @Resource(name = "JwtConfigBase")
    private JwtConfig jwtConfig;

    @Value("${minio.bucket.files}")
    private String bucket;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private UploadFiles uploadFiles;

    @Override
    public String loginForAdmin(Administrator administrator) {
        LambdaQueryWrapper<Administrator> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Administrator::getUsername, administrator.getUsername());
        Administrator adminFromDb = administratorMapper.selectOne(wrapper);
        if (adminFromDb == null)
            throw new PMSException("没有该用户! or 用户名错误!", Error.NO_SUCH_USER);
        boolean equals = adminFromDb.getPassword().equals(administrator.getPassword());
        if (!equals)
            throw new PMSException("密码错误!", Error.WRONG_PASSWORD);
        administrator.setPassword("");
        String s = JSONUtil.toJsonStr(administrator);
        if (adminFromDb.getAuthority() == null)
            throw new PMSException("没有对应的权限设置!", Error.UNAUTHORIZED);
        return jwtConfig.createToken(s, adminFromDb.getAid(), adminFromDb.getAuthority());
    }

    @Override
    public boolean registerForAdmin(Administrator administrator) {
        if (administrator.getAuthority() == null)
            throw new PMSException("请输入身份!", Error.UNAUTHORIZED);
        LambdaQueryWrapper<Administrator> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Administrator::getUsername, administrator.getUsername());
        Administrator adminFromDb = administratorMapper.selectOne(wrapper);
        if (adminFromDb != null)
            throw new PMSException("用户名重复!", Error.SAME_USERNAME);
        administratorMapper.insert(administrator);
        return true;
    }

    @Override
    public RestResponse<Administrator> getCurrentAdminInfo(Integer id, String auth) {
        Administrator administrator = administratorMapper.selectById(id);
        if (administrator == null)
            return RestResponse.validFail("没有该对象!", Error.DATABASE_SELECT_FAILED);
        administrator.setPassword("");
        if (administrator.getAuthority().equals(auth))
            return RestResponse.success(administrator, "success", Valid.DATABASE_SELECT_SUCCESS);
        return RestResponse.validFail("没有权限!", Error.UNAUTHORIZED);
    }

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
