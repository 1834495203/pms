package com.example.form.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
        return null;
    }
}
