package com.example.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.auth.dto.QueryVisitorDto;
import com.example.auth.dto.ResultVisitorDto;
import com.example.auth.dto.VisitorPostDto;
import com.example.auth.po.Visitor;
import com.example.model.PageParams;
import com.example.model.PageResult;
import com.example.model.RestResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GLaDOS
 * @since 2023-03-20
 */
public interface VisitorService extends IService<Visitor> {

    /**
     * 提交访客表单信息
     * @param visitorPostDto 访客表单信息
     * @return visitor
     */
    RestResponse<Visitor> postVisitorInfo(VisitorPostDto visitorPostDto);

    /**
     * 查询返回访客数据
     * @param queryVisitorDto 查询访客数据
     * @return 分页数据
     */
    PageResult<ResultVisitorDto> getVisitorInfo(QueryVisitorDto queryVisitorDto, PageParams pageParams);

    /**
     * 根据门牌查询访客信息
     * @param doorplate 门牌
     * @return 访客信息
     */
    List<Visitor> getVisitorByDoorplate(@PathVariable String doorplate);
}
