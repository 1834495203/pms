package com.example.personnel.service.impl;

import com.example.personnel.model.po.Administrator;
import com.example.personnel.mapper.AdministratorMapper;
import com.example.personnel.service.AdministratorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

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

}
