package com.example.form.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.form.model.dto.PostComplaintDto;
import com.example.form.model.dto.QueryComplaintDto;
import com.example.form.model.dto.UpdateComplaintDto;
import com.example.form.model.po.Complaint;
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
public interface ComplaintService extends IService<Complaint> {

    /**
     * 写入投诉
     * @param complaint 投诉的信息
     * @return RR
     */
    RestResponse<Complaint> postComplaint(PostComplaintDto complaint);

    /**
     * 根据业主的id获取投诉的内容
     * @param pageParams 分页信息
     * @param propId 业主id
     * @return 分页内容
     */
    PageResult<Complaint> getComplaintByUserId(PageParams pageParams, Integer propId);

    /**
     * 根据投诉的cid获删除投诉
     * @param cid 投诉cid
     * @param auth 权限
     * @return RR
     */
    RestResponse<Complaint> deleteComplaintByCid(Integer cid, String auth, Integer uid);

    /**
     * 根据query类查询投诉信息
     * @param queryComplaintDto 查询的条件
     * @return 分页内容
     */
    PageResult<Complaint> selectComplaint(PageParams pageParams, QueryComplaintDto queryComplaintDto);

    /**
     * 修改投诉中的相关信息
     * @param updateComplaintDto 修改的信息
     * @return RR
     */
    RestResponse<Complaint> updateComplaint(String userAuth, UpdateComplaintDto updateComplaintDto);
}
