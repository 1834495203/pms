package com.example.form.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.exception.Error;
import com.example.form.mapper.InformationMapper;
import com.example.form.model.po.Information;
import com.example.form.service.InformationService;
import com.example.model.RestResponse;
import com.example.model.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class InformationServiceImpl extends ServiceImpl<InformationMapper, Information> implements InformationService {

    @Autowired
    private InformationMapper informationMapper;

    @Override
    public RestResponse<Information> selectInfoByHid(Integer id) {
        LambdaQueryWrapper<Information> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Information::getHid, id);
        Information information = informationMapper.selectOne(wrapper);
        if (information == null)
            RestResponse.validFail("暂无该房产的信息", Error.DATABASE_SELECT_FAILED);
        return RestResponse.success(information, "查询成功", Valid.DATABASE_SELECT_SUCCESS);
    }
}
