package com.example.form.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.exception.Error;
import com.example.exception.PMSException;
import com.example.form.mapper.ComplaintMapper;
import com.example.form.mapper.PictMapper;
import com.example.form.model.dto.PostComplaintDto;
import com.example.form.model.dto.QueryComplaintDto;
import com.example.form.model.dto.ResultComplaintDto;
import com.example.form.model.dto.UpdateComplaintDto;
import com.example.form.model.po.Complaint;
import com.example.form.model.po.Pict;
import com.example.form.service.ComplaintService;
import com.example.form.util.String2Map;
import com.example.model.PageParams;
import com.example.model.PageResult;
import com.example.model.RestResponse;
import com.example.model.Valid;
import com.example.utils.HasAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private PictMapper pictMapper;

    @Override
    public RestResponse<Complaint> postComplaint(PostComplaintDto complaint) {
        //string转map
        Map<String, Integer> hashMap = String2Map.string2map(complaint.getProfiles());
        Complaint complaint2Db = new Complaint();
        BeanUtil.copyProperties(complaint, complaint2Db);

        StringBuffer sb = new StringBuffer();
        hashMap.values().forEach(v->sb.append(v).append(","));
        //减去最后的逗号
        sb.deleteCharAt(sb.length()-1);
        complaint2Db.setProfile(sb.toString());
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
    public PageResult<ResultComplaintDto> selectComplaint(PageParams pageParams, QueryComplaintDto queryComplaintDto) {
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
        if (queryComplaintDto.getCreateDate() != null){
            //之前
            if (queryComplaintDto.getQueryTime() == -1)
                wrapper.lt(Complaint::getCreateDate, queryComplaintDto.getCreateDate());
            //之后
            else if (queryComplaintDto.getQueryTime() == 1)
                wrapper.gt(Complaint::getCreateDate, queryComplaintDto.getCreateDate());
            //等于
            else wrapper.like(Complaint::getCreateDate, queryComplaintDto.getCreateDate());
        }
        Page<Complaint> complaintPage = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<Complaint> page = complaintMapper.selectPage(complaintPage, wrapper);
        //查询图片信息
        ArrayList<ResultComplaintDto> resultComplaintDto = new ArrayList<>();
        for (Complaint record : page.getRecords()) {
            //遍历分页的数据
            ResultComplaintDto result = new ResultComplaintDto();
            BeanUtil.copyProperties(record, result);
            String profile = record.getProfile();
            if (profile != null){
                String[] split = profile.split(",");
                ArrayList<String> names = new ArrayList<>();
                for (String s : split) {
                    Pict pict = pictMapper.selectById(s);
                    //将图片信息写入结果
                    names.add(pict.getObjectName());
                    //设置图片信息对应的投诉id
                    if (pict.getProfileId() == null)
                        pict.setProfileId(record.getCid());
                    pictMapper.updateById(pict);
                }
                result.setObjectName(names);
            }
            resultComplaintDto.add(result);
        }
        return new PageResult<>(resultComplaintDto, page.getTotal(), pageParams.getPageNo(),
                pageParams.getPageSize());
    }

    @Override
    public RestResponse<Complaint> updateComplaint(String userAuth, UpdateComplaintDto updateComplaintDto) {
        Integer cid = updateComplaintDto.getCid();
        HasAuth<UpdateComplaintDto> updateComplaintDtoHasAuth = new HasAuth<>(userAuth, updateComplaintDto, UpdateComplaintDto.class);
        UpdateComplaintDto afterAuth = updateComplaintDtoHasAuth.afterAuth();

        //从数据库根据cid查询投诉表
        LambdaQueryWrapper<Complaint> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Complaint::getCid, cid);
        Complaint cFromDb = complaintMapper.selectOne(wrapper);
        if (cFromDb == null)
            return RestResponse.validFail("没有此投诉单", Error.DATABASE_SELECT_FAILED);

        //如果为业主 且id不相等 则抛出
        if (userAuth.contains("910") && !cFromDb.getPubilsherId().equals(updateComplaintDto.getPubilsherId()))
            throw new PMSException("不能修改除自己以外的信息", Error.UNAUTHORIZED);

        //忽略null字段
        BeanUtil.copyProperties(afterAuth, cFromDb, CopyOptions.create().ignoreNullValue());
        if (complaintMapper.updateById(cFromDb) == 1)
            return RestResponse.success(cFromDb, "投诉表更新成功", Valid.DATABASE_UPDATE_SUCCESS);
        return RestResponse.validFail(cFromDb, "投诉表更新失败", Error.DATABASE_UPDATE_FAILED);
    }
}
