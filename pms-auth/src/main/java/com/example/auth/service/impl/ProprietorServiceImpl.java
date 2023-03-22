package com.example.auth.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.config.JwtConfig;
import com.example.exception.Error;
import com.example.exception.PMSException;
import com.example.auth.mapper.ProprietorMapper;
import com.example.auth.po.Proprietor;
import com.example.auth.service.ProprietorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
