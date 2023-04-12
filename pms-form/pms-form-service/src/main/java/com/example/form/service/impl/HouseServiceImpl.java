package com.example.form.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.exception.Error;
import com.example.exception.PMSException;
import com.example.form.mapper.HouseMapper;
import com.example.form.model.dto.ResultHouseDto;
import com.example.form.model.po.House;
import com.example.form.service.HouseService;
import com.example.model.RestResponse;
import com.example.model.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GLaDOS
 */
@Slf4j
@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Override
    public RestResponse<ResultHouseDto> getBuildingInfoById(Integer id) {
        Integer parent = 0;
        Map<Integer, List<ResultHouseDto>> houseDtoHashMap = new LinkedHashMap<>();
        ArrayList<ResultHouseDto> resultHouseDto = new ArrayList<>();
        List<House> houseInfoByOrder = houseMapper.getHouseInfoByOrder(id);
        if (houseInfoByOrder == null)
            return RestResponse.validFail("没有对象", Error.DATABASE_SELECT_FAILED);
        try{
            for (House house : houseInfoByOrder) {
                //遍历全部对象
                if (!house.getParent().equals(parent)){
                    //当单个对象的父节点不等于parent时将resultHouseDto保存在map中并新引用一个resultHouseDto
                    houseDtoHashMap.put(parent, resultHouseDto);
                    resultHouseDto = new ArrayList<>();
                    parent = house.getParent();
                }
                ResultHouseDto temp = new ResultHouseDto();
                BeanUtil.copyProperties(house, temp);
                resultHouseDto.add(temp);
            }
            if (resultHouseDto.size() > 0)
                houseDtoHashMap.put(parent, resultHouseDto);

            List<Integer> collect = new ArrayList<>(houseDtoHashMap.keySet());
            Collections.reverse(collect);

            //类似冒泡排序
            for (int i = 0; i < collect.size(); i++) {
                for (int j = i+1; j < collect.size(); j++) {
                    for (ResultHouseDto houseDto : houseDtoHashMap.get(collect.get(j))) {
                        if (collect.get(i).equals(houseDto.getHid())){
                            houseDto.setChildren(houseDtoHashMap.get(collect.get(i)));
                            break;
                        }
                    }
                }
            }

            ResultHouseDto finalResult = houseDtoHashMap.get(0).get(0);
            finalResult.setLastId(houseMapper.getHouseLastId());
            return RestResponse.success(finalResult, "查询成功", Valid.DATABASE_SELECT_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            throw new PMSException("获取楼栋信息失败", Error.DATABASE_SELECT_FAILED);
        }
    }

    @Override
    public RestResponse<House> appendBuildingInfo(House house) {
        if (house == null)
            RestResponse.validFail("没有该对象", Error.FAILED);
        House house2Db = new House();
        BeanUtil.copyProperties(house, house2Db);
        if (houseMapper.insert(house2Db) == 1)
            return RestResponse.success(house2Db, "添加成功", Valid.DATABASE_INSERT_SUCCESS);
        return RestResponse.validFail("添加失败", Error.DATABASE_INSERT_FAILED);
    }

    @Override
    public RestResponse<ResultHouseDto> deleteBuildingInfo(ResultHouseDto resultHouseDto) {
        List<ResultHouseDto> children;
        int size = 0, total = 0;
        if ((children=resultHouseDto.getChildren()) != null){
            //如果被删除的不是叶子节点
            size = children.size();
            for (ResultHouseDto child : children) {
                total += houseMapper.deleteById(child.getHid());
            }
        }
        houseMapper.deleteById(resultHouseDto.getHid());
        if (size+1 == total+1) return RestResponse.success(resultHouseDto, "删除成功", Valid.DATABASE_DELETE_SUCCESS);
        return RestResponse.validFail("删除失败", Error.DATABASE_DELETE_FAILED);
    }
}
