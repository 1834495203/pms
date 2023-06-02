package com.example.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.auth.po.Proprietor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author GLaDOS
 */
@Mapper
public interface ProprietorMapper extends BaseMapper<Proprietor> {

    String selectNameById(Integer pid);

    List<Integer> selectCount();
}
