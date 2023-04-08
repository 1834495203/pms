package com.example.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.config.JwtConfig;
import com.example.auth.dto.ResultUserBaseInfo;
import com.example.auth.util.UploadFiles;
import com.example.exception.Error;
import com.example.exception.PMSException;
import com.example.auth.mapper.ProprietorMapper;
import com.example.auth.po.Proprietor;
import com.example.auth.service.ProprietorService;
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
public class ProprietorServiceImpl extends ServiceImpl<ProprietorMapper, Proprietor> implements ProprietorService {

    @Autowired
    private ProprietorMapper proprietorMapper;

    @Resource(name = "JwtConfigBase")
    private JwtConfig jwtConfig;

    @Value("${minio.bucket.files}")
    private String bucket;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private UploadFiles uploadFiles;

    @Override
    public String loginForProprietor(Proprietor proprietor) {
        LambdaQueryWrapper<Proprietor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Proprietor::getUsername, proprietor.getUsername());
        Proprietor proprietorFromDb = proprietorMapper.selectOne(wrapper);
        if (proprietorFromDb == null)
            throw new PMSException("没有该用户", Error.NO_SUCH_USER);
        boolean equals = proprietorFromDb.getPassword().equals(proprietor.getPassword());
        if (!equals)
            throw new PMSException("密码错误", Error.WRONG_PASSWORD);
        //将密码抹去
        proprietor.setPassword("");
        String s = JSONUtil.toJsonStr(proprietor);
        if (proprietorFromDb.getStatus() == null)
            throw new PMSException("用户身份错误", Error.NO_AUTH);
        return jwtConfig.createToken(s, proprietorFromDb.getPid(), proprietorFromDb.getStatus());
    }

    @Override
    public boolean registerForProprietor(Proprietor proprietor) {
        if (proprietor.getStatus() == null)
            throw new PMSException("请输入身份!", Error.UNAUTHORIZED);
        LambdaQueryWrapper<Proprietor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Proprietor::getUsername, proprietor.getUsername());
        Proprietor proFromDb = proprietorMapper.selectOne(wrapper);
        if (proFromDb != null)
            throw new PMSException("用户名重复!", Error.SAME_USERNAME);
        proprietorMapper.insert(proprietor);
        return true;
    }

    @Override
    public RestResponse<Proprietor> getCurrentProprietor(Integer id, String auth) {
        Proprietor proprietor = proprietorMapper.selectById(id);
        if (proprietor == null) return RestResponse.validFail("没有该对象!", Error.DATABASE_SELECT_FAILED);
        proprietor.setPassword("");
        if (proprietor.getStatus().equals(auth))
            return RestResponse.success(proprietor, "success", Valid.DATABASE_SELECT_SUCCESS);
        return RestResponse.validFail("没有该权限", Error.UNAUTHORIZED);
    }

    @Transactional
    @Override
    public RestResponse<?> upLoadUserProfile(String filename,
                                             String localFilePath,
                                             Integer id) {
        Proprietor proprietor = proprietorMapper.selectById(id);
        return uploadFiles.uploadFile(filename, localFilePath, proprietorMapper, bucket, proprietor, minioClient);
    }

    @Override
    public ResultUserBaseInfo getUserBaseInfo(Integer id) {
        Proprietor proprietor = proprietorMapper.selectById(id);
        if (proprietor == null) return null;
        ResultUserBaseInfo resultUserBaseInfo = new ResultUserBaseInfo();
        BeanUtil.copyProperties(proprietor, resultUserBaseInfo);
        return resultUserBaseInfo;
    }
}
