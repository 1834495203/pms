package com.example.form.util;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.exception.PMSException;
import com.example.form.mapper.PictMapper;
import com.example.form.model.po.Pict;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author GLaDOS
 * @date 2023/4/5 18:05
 */
public class General {

    /**
     *
     * @param dClazz dto.class
     * @param listDto Array(dto)
     * @param <O> po
     * @param <D> dto
     */
    public <O, D> void fillPictInfo(Class<? extends D> dClazz,
                                       List<D> listDto, Page<O> page,
                                       PictMapper pictMapper){
        try{
            //遍历获取的分页信息
            for (O record : page.getRecords()) {
                //实例化一个dto
                D result = dClazz.getDeclaredConstructor().newInstance();
                //获取result的objectName变量
                Field objectName = result.getClass().getDeclaredField("objectName");
                objectName.setAccessible(true);
                //将单个分页信息拷贝到dto中
                BeanUtil.copyProperties(record, result);
                //获取单个分页信息的profile变量与id变量
                Field profile = record.getClass().getDeclaredField("profile");
                Field id = Arrays.stream(record.getClass().getDeclaredFields()).filter(field -> field.getName().contains("id")).findFirst().orElse(null);
                if (id != null)
                    id.setAccessible(true);
                profile.setAccessible(true);
                String profiles = (String) profile.get(record);
                if (profiles != null){
                    ArrayList<String> names = new ArrayList<>();
                    for (String s : profiles.split(",")) {
                        //获取对应的pict信息
                        Pict pict = pictMapper.selectById(s);
                        //将图片信息写入结果
                        names.add(pict.getObjectName());
                        //设置图片信息对应的ProfileId
                        if (pict.getProfileId() == null && id != null){
                            pict.setProfileId((Integer) id.get(record));
                        }
                        pictMapper.updateById(pict);
                    }
                    objectName.set(result, names);
                }
                listDto.add(result);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new PMSException("无法获取pict对应信息");
        }
    }
}
