package com.example.auth.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.mapper.AdministratorMapper;
import com.example.auth.po.Administrator;
import com.example.auth.service.AdministratorService;
import com.example.auth.config.JwtConfig;
import com.example.exception.Error;
import com.example.exception.PMSException;
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
public class AdministratorServiceImpl extends ServiceImpl<AdministratorMapper, Administrator> implements AdministratorService {

    @Autowired
    private AdministratorMapper administratorMapper;

    @Resource(name = "JwtConfigBase")
    private JwtConfig jwtConfig;

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
        String s = JSONUtil.toJsonStr(adminFromDb);
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
}
