package com.example.form.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.form.model.po.House;
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
public interface HouseMapper extends BaseMapper<House> {

    /**
     * 通过楼栋id查询整栋楼的信息
     * @param id 楼栋id
     * @return list
     */
    List<House> getHouseInfoByOrder(Integer id);

    /**
     * 获取最后一个值的id
     * @return int
     */
    Integer getHouseLastId();
}
