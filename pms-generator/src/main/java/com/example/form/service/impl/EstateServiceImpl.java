package com.example.form.service.impl;

import com.example.form.model.po.Estate;
import com.example.form.mapper.EstateMapper;
import com.example.form.service.EstateService;
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
public class EstateServiceImpl extends ServiceImpl<EstateMapper, Estate> implements EstateService {

}
