package com.example.form.model.po.service.impl;

import com.example.form.model.po.Complaint;
import com.example.form.mapper.ComplaintMapper;
import com.example.form.model.po.service.ComplaintService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
public class ComplaintServiceImpl extends ServiceImpl<ComplaintMapper, Complaint> implements ComplaintService {

}
