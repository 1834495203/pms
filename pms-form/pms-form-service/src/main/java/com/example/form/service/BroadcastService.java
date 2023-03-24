package com.example.form.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.form.model.dto.QueryBroadcastDto;
import com.example.form.model.dto.UpdateBroadcastDto;
import com.example.form.model.po.Broadcast;
import com.example.model.PageParams;
import com.example.model.PageResult;
import com.example.model.RestResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GLaDOS
 * @since 2023-03-21
 */
public interface BroadcastService extends IService<Broadcast> {

    /**
     * 发表公告
     * @param broadcast 公告内容
     * @return RR
     */
    RestResponse<Broadcast> postBroadcast(Broadcast broadcast);

    /**
     * 条件更新公告
     * @param updateBroadcastDto 更新信息
     * @return RR
     */
    RestResponse<Broadcast> updateBroadcast(UpdateBroadcastDto updateBroadcastDto);

    /**
     * 条件查询公告信息
     * @param pageParams 分页信息
     * @param queryBroadcastDto 公告信息
      * @param userAuth 用户权限
     * @return PR
     */
    PageResult<Broadcast> queryBroadcast(PageParams pageParams, QueryBroadcastDto queryBroadcastDto, String userAuth);
}
