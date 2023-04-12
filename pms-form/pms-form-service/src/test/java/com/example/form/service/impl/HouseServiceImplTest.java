package com.example.form.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.form.mapper.HouseMapper;
import com.example.form.model.dto.ResultHouseDto;
import com.example.form.model.po.House;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 楼栋管理测试
 * @author GLaDOS
 * @date 2023/4/9 20:28
 */
@SpringBootTest
class HouseServiceImplTest {

    @Autowired
    private HouseMapper houseMapper;

    @Test
    void testQuery(){
        Integer parent = 0;
        HashMap<Integer, List<ResultHouseDto>> houseDtoHashMap = new HashMap<>();
        ArrayList<ResultHouseDto> resultHouseDto = new ArrayList<>();
        List<House> houseInfoByOrder = houseMapper.getHouseInfoByOrder(1);

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

        for (int i = 0; i < collect.size(); i++) {
            for (int j = i+1; j < collect.size(); j++) {
                for (ResultHouseDto houseDto : houseDtoHashMap.get(collect.get(j))) {
                    if (collect.get(i).equals(houseDto.getHid())){
                        houseDto.setChildren(houseDtoHashMap.get(collect.get(i)));
                    }
                }
            }
        }
        System.out.println(houseDtoHashMap.get(0).get(0));
    }
}