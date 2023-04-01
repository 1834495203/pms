package com.example.form.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.exception.Error;
import com.example.exception.PMSException;
import com.example.form.mapper.BroadcastMapper;
import com.example.form.model.dto.QueryBroadcastDto;
import com.example.form.model.dto.UpdateBroadcastDto;
import com.example.form.model.po.Broadcast;
import com.example.form.service.BroadcastService;
import com.example.model.PageParams;
import com.example.model.PageResult;
import com.example.model.RestResponse;
import com.example.model.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

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

    @Override
    public RestResponse<Broadcast> postBroadcast(Broadcast broadcast) {
        if (broadcast.getCreateDate() == null)
            broadcast.setCreateDate(LocalDateTime.now());
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
    public PageResult<Broadcast> queryBroadcast(PageParams pageParams,
                                                QueryBroadcastDto queryBroadcastDto,
                                                String userAuth) {
        //鉴权
        if (!userAuth.contains("90090") ||
                !queryBroadcastDto.getCorrespond().equals(userAuth) ||
        !queryBroadcastDto.getCorrespond().equals("999"))
            throw new PMSException("没有权限调用此api", Error.UNAUTHORIZED);

        //TODO 公告查询
        LambdaQueryWrapper<Broadcast> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Broadcast::getCorrespond, userAuth);
        //模糊查询
        if (queryBroadcastDto.getTitle() != null)
            wrapper.like(Broadcast::getTitle, queryBroadcastDto.getTitle());
        //时间查询
        if (queryBroadcastDto.getCreateDate() != null){
            if (queryBroadcastDto.getQueryTime() == null || queryBroadcastDto.getQueryTime() == 0)
                wrapper.eq(Broadcast::getCreateDate, queryBroadcastDto.getCreateDate());
            else if (queryBroadcastDto.getQueryTime() > 0)
                wrapper.gt(Broadcast::getCreateDate, queryBroadcastDto.getCreateDate());
            else wrapper.lt(Broadcast::getCreateDate, queryBroadcastDto.getCreateDate());
        }
        //精确查询
        HashMap<SFunction<Broadcast, ?>, Object> map = new HashMap<>();
        map.put(Broadcast::getPubilsherId, queryBroadcastDto.getPubilsherId());
        map.put(Broadcast::getState, queryBroadcastDto.getState());
        wrapper.allEq(map);
        //分页查询
        Page<Broadcast> broadcastPage = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<Broadcast> pages = broadcastMapper.selectPage(broadcastPage, wrapper);
        if (pages.getTotal() == 0)
            throw new PMSException("没有对应的信息", Error.DATABASE_SELECT_FAILED);
        return new PageResult<>(pages.getRecords(), pages.getTotal(),
                pageParams.getPageNo(), pageParams.getPageSize());
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
