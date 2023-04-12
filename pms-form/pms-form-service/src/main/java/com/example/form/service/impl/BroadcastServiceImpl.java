package com.example.form.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.exception.Error;
import com.example.exception.PMSException;
import com.example.form.client.UserInfoClient;
import com.example.form.mapper.BroadcastMapper;
import com.example.form.mapper.PictMapper;
import com.example.form.model.dto.*;
import com.example.form.model.po.Broadcast;
import com.example.form.model.po.Complaint;
import com.example.form.service.BroadcastService;
import com.example.form.util.General;
import com.example.form.util.String2Map;
import com.example.model.PageParams;
import com.example.model.PageResult;
import com.example.model.RestResponse;
import com.example.model.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GLaDOS
 */
@Slf4j
@Service
public class BroadcastServiceImpl extends ServiceImpl<BroadcastMapper, Broadcast> implements BroadcastService {

    @Autowired
    private BroadcastMapper broadcastMapper;

    @Autowired
    private UserInfoClient userInfoClient;

    @Autowired
    private PictMapper pictMapper;

    @Override
    public RestResponse<Broadcast> postBroadcast(Broadcast broadcast) {
        //string转map
        Map<String, Integer> hashMap = String2Map.string2map(broadcast.getProfile());
        StringBuffer sb = new StringBuffer();
        hashMap.values().forEach(v->sb.append(v).append(","));
        //减去最后的逗号
        sb.deleteCharAt(sb.length()-1);
        broadcast.setProfile(sb.toString());
        if (broadcast.getCreateTime() == null)
            broadcast.setCreateTime(LocalDateTime.now());
        if (broadcast.getCreateDate() == null)
            broadcast.setCreateDate(LocalDate.now());
        if (broadcastMapper.insert(broadcast) == 1)
            return RestResponse.success(broadcast, "发表成功", Valid.DATABASE_INSERT_SUCCESS);
        return RestResponse.validFail(broadcast, "发表失败", Error.DATABASE_INSERT_FAILED);
    }

    @Override
    public RestResponse<Broadcast> updateBroadcast(UpdateBroadcastDto updateBroadcastDto) {
        LambdaQueryWrapper<Broadcast> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Broadcast::getBid, updateBroadcastDto.getBid());
        Broadcast broadcast = broadcastMapper.selectOne(wrapper);
        if (broadcast == null)
            return RestResponse.validFail("没有该对象", Error.DATABASE_SELECT_FAILED);

        //将修改信息拷贝到原对象中
        BeanUtil.copyProperties(updateBroadcastDto, broadcast, CopyOptions.create().ignoreNullValue());
        //写入
        if (broadcastMapper.updateById(broadcast) == 1)
            return RestResponse.success(broadcast, "更新成功", Valid.DATABASE_UPDATE_SUCCESS);
        return RestResponse.validFail("更新失败", Error.DATABASE_UPDATE_FAILED);
    }

    @Override
    public PageResult<ResultBroadcastDto> queryBroadcast(PageParams pageParams,
                                                QueryBroadcastDto queryBroadcastDto,
                                                String userAuth) {
        LambdaQueryWrapper<Broadcast> wrapper = new LambdaQueryWrapper<>();

        //模糊查询
        if (queryBroadcastDto.getTitle() != null)
        wrapper.like(Broadcast::getTitle, queryBroadcastDto.getTitle());

        //根据发布的时间查询
        if (queryBroadcastDto.getQueryDate() != null && queryBroadcastDto.getQueryDate().size() > 0){
            wrapper.ge(Broadcast::getCreateDate, queryBroadcastDto.getQueryDate().get(0));
            wrapper.le(Broadcast::getCreateDate, queryBroadcastDto.getQueryDate().get(1));
        }

        Page<Broadcast> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<Broadcast> broadcastPage = broadcastMapper.selectPage(page, wrapper);

        //查询图片信息
        ArrayList<ResultBroadcastDto> resultBroadcastDto = new ArrayList<>();
        new General().fillPictInfo(ResultBroadcastDto.class, resultBroadcastDto, broadcastPage, pictMapper);

        resultBroadcastDto.forEach(res -> {
            ResultUserBaseInfo authUserBaseInfoById = userInfoClient.getAuthUserBaseInfoById(res.getPubilsherId());
            if (authUserBaseInfoById != null)
                res.setUsername(authUserBaseInfoById.getUsername());
        });

        List<ResultBroadcastDto> collect = resultBroadcastDto;
        //如果查询者为业主
        if (userAuth.contains("910")) {
            collect = resultBroadcastDto.stream().filter(res -> !res.getCorrespond().equals("900")).collect(Collectors.toList());
        }

        return new PageResult<>(collect, page.getTotal(), pageParams.getPageNo(),
                pageParams.getPageSize());
    }

    @Override
    public RestResponse<Broadcast> deleteBroadcastById(Integer bid, Integer uid) {
        Broadcast broadcast = broadcastMapper.selectById(bid);
        if (broadcast == null)
            return RestResponse.validFail("没有该对象", Error.DATABASE_SELECT_FAILED);
        if (!broadcast.getPubilsherId().equals(uid))
            return RestResponse.validFail("没有权限调用此api", Error.UNAUTHORIZED);
        if (broadcastMapper.deleteById(bid) == 1)
            return RestResponse.success(broadcast, "删除成功", Valid.DATABASE_DELETE_SUCCESS);
        return RestResponse.validFail("删除失败", Error.DATABASE_DELETE_FAILED);
    }
}
