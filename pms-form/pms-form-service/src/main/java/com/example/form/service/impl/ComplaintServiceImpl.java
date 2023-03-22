package com.example.form.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.exception.Error;
import com.example.form.mapper.ComplaintMapper;
import com.example.form.model.dto.PostComplaintDto;
import com.example.form.model.dto.QueryComplaintDto;
import com.example.form.model.po.Complaint;
import com.example.form.service.ComplaintService;
import com.example.model.PageParams;
import com.example.model.PageResult;
import com.example.model.RestResponse;
import com.example.model.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ComplaintServiceImpl extends ServiceImpl<ComplaintMapper, Complaint> implements ComplaintService {

    @Autowired
    private ComplaintMapper complaintMapper;

    @Override
    public RestResponse<Complaint> postComplaint(PostComplaintDto complaint) {
        Complaint complaint2Db = new Complaint();
        BeanUtil.copyProperties(complaint, complaint2Db);
        if (complaintMapper.insert(complaint2Db) == 1)
            return RestResponse.success(complaint2Db);
        return RestResponse.validFail(complaint2Db, "未知错误, 插入失败!",
                Error.DATABASE_INSERT_FAILED);
    }

    @Override
    public PageResult<Complaint> getComplaintByUserId(PageParams pageParams, Integer propId) {
        //根据id查询对应的投诉单
        LambdaQueryWrapper<Complaint> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Complaint::getPubilsherId, propId);

        Page<Complaint> complaintPage = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<Complaint> result = complaintMapper.selectPage(complaintPage, wrapper);
        //数据列表
        List<Complaint> records = result.getRecords();
        //总记录数
        long total = result.getTotal();
        return new PageResult<>(records, total, pageParams.getPageNo(), pageParams.getPageSize());
    }

    @Override
    public RestResponse<Complaint> deleteComplaintByCid(Integer cid, String auth, Integer uid) {
        //根据权限确定删除
        LambdaQueryWrapper<Complaint> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Complaint::getCid, cid);
        Complaint complaint = complaintMapper.selectOne(wrapper);
        if (complaint == null)
            return RestResponse.validFail("没有该投诉单, 删除失败!", Error.DATABASE_SELECT_FAILED);
        if (auth.contains("910")){
            //业主仅能删除自己发送的投诉
            if (complaint.getPubilsherId().equals(uid))
                return RestResponse.validFail("不能删除除自己以外的投诉单!", Error.UNAUTHORIZED);
            complaintMapper.deleteById(cid);
            return RestResponse.success(complaint, "删除成功", Valid.DATABASE_DELETE_SUCCESS);
        }else if (auth.contains("90093")){
            //前台可以删除任意投诉单
            complaintMapper.deleteById(cid);
            return RestResponse.success(complaint, "删除成功", Valid.DATABASE_DELETE_SUCCESS);
        }
        return RestResponse.validFail("删除失败!", Error.DATABASE_DELETE_FAILED);
    }

    @Override
    public PageResult<Complaint> selectComplaint(PageParams pageParams, QueryComplaintDto queryComplaintDto) {
        LambdaQueryWrapper<Complaint> wrapper = new LambdaQueryWrapper<>();
        //根据状态查询
        if (queryComplaintDto.getState() != null)
            wrapper.eq(Complaint::getState, queryComplaintDto.getState());
        //根据业主id查询
        if (queryComplaintDto.getPubilsherId() != null)
            wrapper.eq(Complaint::getPubilsherId, queryComplaintDto.getPubilsherId());
        //根据投诉标题查询
        if (queryComplaintDto.getTitle() != null)
            wrapper.like(Complaint::getTitle, queryComplaintDto.getTitle());
        //根据发布的时间查询
        if (queryComplaintDto.getCreateTime() != null){
            //之前
            if (queryComplaintDto.getQueryTime() == -1)
                wrapper.lt(Complaint::getCreateTime, queryComplaintDto.getCreateTime());
            //之后
            else if (queryComplaintDto.getQueryTime() == 1)
                wrapper.gt(Complaint::getCreateTime, queryComplaintDto.getCreateTime());
            //等于
            else wrapper.eq(Complaint::getCreateTime, queryComplaintDto.getCreateTime());
        }
        Page<Complaint> complaintPage = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<Complaint> page = complaintMapper.selectPage(complaintPage, wrapper);

        return new PageResult<>(page.getRecords(), page.getTotal(), pageParams.getPageNo(),
                pageParams.getPageSize());
    }
}
