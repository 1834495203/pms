package com.example.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.client.Prop2House;
import com.example.auth.config.JwtConfig;
import com.example.auth.dto.QueryPersonnelDto;
import com.example.auth.dto.QueryPropDto;
import com.example.auth.dto.ResultUserBaseInfo;
import com.example.auth.dto.UpdatePropDto;
import com.example.auth.util.UploadFiles;
import com.example.exception.Error;
import com.example.exception.PMSException;
import com.example.auth.mapper.ProprietorMapper;
import com.example.auth.po.Proprietor;
import com.example.auth.service.ProprietorService;
import com.example.model.PageParams;
import com.example.model.PageResult;
import com.example.model.RestResponse;
import com.example.model.Valid;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Resource(name = "JwtConfigBase")
    private JwtConfig jwtConfig;

    @Value("${minio.bucket.files}")
    private String bucket;

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private UploadFiles uploadFiles;

    @Autowired
    private Prop2House prop2House;

    private static final String DEFAULT_PASSWORD = "123456";

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
        if (proprietor.getPassword() == null || proprietor.getPassword().equals(DigestUtil.md5Hex(""))){
            //默认密码为123456
            proprietor.setPassword(DigestUtil.md5Hex(DEFAULT_PASSWORD));
        }
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

    @Override
    public PageResult<QueryPropDto> getListProprietor(PageParams pageParams, QueryPersonnelDto queryPersonnelDto) {
        LambdaQueryWrapper<Proprietor> wrapper = new LambdaQueryWrapper<>();
        String username, name;
        Integer expense;
        if ((username = queryPersonnelDto.getUsername()) != null)
            wrapper.like(Proprietor::getUsername, username);
        if ((name = queryPersonnelDto.getName()) != null)
            wrapper.like(Proprietor::getName, name);
        if ((expense = queryPersonnelDto.getPropertyExpenseState()) != null)
            wrapper.eq(Proprietor::getPropertyExpenseState, expense);
        Page<Proprietor> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<Proprietor> proprietorPage = proprietorMapper.selectPage(page, wrapper);

        ArrayList<QueryPropDto> queryPropDto = new ArrayList<>();
        for (Proprietor record : proprietorPage.getRecords()) {
            QueryPropDto queryProp = new QueryPropDto();
            BeanUtil.copyProperties(record, queryProp);
            ArrayList<Integer> address = new ArrayList<>();
            if (record.getAddress() != null)
                for (String s : record.getAddress().split(",")) {
                    if (s != null)
                        address.add(Integer.valueOf(s));
                }
            queryProp.setAddressList(address);
            queryPropDto.add(queryProp);
        }
        return new PageResult<>(queryPropDto, proprietorPage.getTotal(), pageParams.getPageNo(), pageParams.getPageSize());
    }

    @Override
    public RestResponse<Proprietor> updateProprietor(UpdatePropDto updatePropDto) {
        Proprietor proprietor = new Proprietor();
        BeanUtil.copyProperties(updatePropDto, proprietor);
        if (proprietorMapper.updateById(proprietor) == 1){
            prop2House.bindHouseInfo2Prop(updatePropDto.getPid(), Integer.valueOf(updatePropDto.getAddress().split(",")[3]));
            return RestResponse.success(proprietor, "更新成功", Valid.DATABASE_UPDATE_SUCCESS);
        }
        return RestResponse.validFail("更新失败", Error.DATABASE_UPDATE_FAILED);
    }
}
