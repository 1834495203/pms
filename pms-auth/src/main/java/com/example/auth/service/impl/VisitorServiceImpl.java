package com.example.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.auth.client.Prop2House;
import com.example.auth.dto.QueryVisitorDto;
import com.example.auth.dto.ResultVisitorDto;
import com.example.auth.dto.VisitorPostDto;
import com.example.auth.mapper.ProprietorMapper;
import com.example.auth.mapper.VisitorMapper;
import com.example.auth.po.Information;
import com.example.auth.po.Proprietor;
import com.example.auth.po.Visitor;
import com.example.auth.service.VisitorService;
import com.example.exception.Error;
import com.example.exception.PMSException;
import com.example.model.PageParams;
import com.example.model.PageResult;
import com.example.model.RestResponse;
import com.example.model.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements VisitorService {

    @Autowired
    private VisitorMapper visitorMapper;

    @Autowired
    private Prop2House prop2House;

    @Autowired
    private ProprietorMapper proprietorMapper;

    @Transactional
    @Override
    public RestResponse<Visitor> postVisitorInfo(VisitorPostDto visitorPostDto) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(visitorPostDto.getRequiredDoorplate()).forEach(sb::append);
        String doorplate = sb.toString();
        Information houseInfoByDoorPlate = prop2House.getHouseInfoByDoorPlate(doorplate);
        if (houseInfoByDoorPlate.getPid() == null) throw new PMSException("没有业主！");

        Visitor visitor = new Visitor();
        visitor.setRequiredTime(visitorPostDto.getRequiredTime()[0]);
        visitor.setGender(visitorPostDto.getGender());
        visitor.setLicensePlate(visitorPostDto.getLicensePlate());
        visitor.setName(visitorPostDto.getName());
        visitor.setRequiredDoorplate(doorplate);
        visitor.setRequiredLeavingTime(visitorPostDto.getRequiredTime()[1]);
        //70010 访客访问中
        visitor.setState("70010");
        visitor.setRequiredTime(visitorPostDto.getRequiredTime()[0]);

        if (visitorMapper.insert(visitor) == 1) return RestResponse.success(visitor, "上传成功", Valid.DATABASE_INSERT_SUCCESS);
        return RestResponse.validFail("上传失败", Error.DATABASE_INSERT_FAILED);
    }

    @Override
    public PageResult<ResultVisitorDto> getVisitorInfo(QueryVisitorDto queryVisitorDto, PageParams pageParams) {
        LambdaQueryWrapper<Visitor> wrapper = new LambdaQueryWrapper<>();
        //姓名
        if (queryVisitorDto.getName() != null)
            wrapper.like(Visitor::getName, queryVisitorDto.getName());

        //车牌号
        if (queryVisitorDto.getLicensePlate() != null)
            wrapper.like(Visitor::getLicensePlate, queryVisitorDto.getLicensePlate());

        //门牌号
        if (queryVisitorDto.getDoorplate() != null)
            wrapper.like(Visitor::getRequiredDoorplate, queryVisitorDto.getDoorplate());

        Page<Visitor> visitorPage = new Page<>(pageParams.getPageNo(), pageParams.getPageSize(), true);
        Page<Visitor> pages = visitorMapper.selectPage(visitorPage, wrapper);

        //填写ResultVisitorDto
        ArrayList<ResultVisitorDto> resultVisitors = new ArrayList<>();
        pages.getRecords().forEach(res -> {
            ResultVisitorDto resultVisitorDto = new ResultVisitorDto();
            BeanUtil.copyProperties(res, resultVisitorDto);
            resultVisitors.add(resultVisitorDto);

            Integer pid = prop2House.getHouseInfoByDoorPlate(res.getRequiredDoorplate()).getPid();
            resultVisitorDto.setPropName(proprietorMapper.selectNameById(pid));
        });

        return new PageResult<>(resultVisitors, pages.getTotal(), pageParams.getPageNo(), pageParams.getPageSize());
    }

    @Override
    public List<Visitor> getVisitorByDoorplate(String doorplate) {
        LambdaQueryWrapper<Visitor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Visitor::getRequiredDoorplate, doorplate);
        //仅查询请求访问的访客
        wrapper.eq(Visitor::getState, "70010");
        return visitorMapper.selectList(wrapper);
    }
}
