package com.example.form;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author GLaDOS
 * @date 2023/3/23 0:16
 */
@SpringBootTest
public class GlobalTest {

    @Test
    void testBeanUtil(){
        UserForTest user = new UserForTest();
        user.setUsername("Marias");

        UserForTest userFromDb = new UserForTest();
        userFromDb.setUsername("Alice");
        userFromDb.setPassword("123456");

        BeanUtil.copyProperties(user, userFromDb, CopyOptions.create().ignoreNullValue());
        System.out.println(userFromDb.getPassword());
    }

    @Test
    void testBean2Map(){
        UserForTest user = new UserForTest();
        user.setUsername("Alice");

        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(user, false, true);

        stringObjectMap.forEach((k, v)-> System.out.println(k+":"+v));
    }

    @Test
    void mapTest(){
        String s = "{\"20200808101240_zhsjy.jpeg\":13,\"toolbar.jpg\":14}";
        StringBuffer sb = new StringBuffer(s);
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length()-1);
        String[] split = sb.toString().split(",");

        HashMap<String, Integer> hashMap = new HashMap<>();
        for (String t : split) {
            String[] kv = t.split(":");
            hashMap.put(kv[0], Integer.valueOf(kv[1]));
        }
        hashMap.forEach((k, v)-> System.out.println(k+":"+v));
    }

    @Test
    void testTime(){
        System.out.println(DateUtil.format(LocalDateTime.now(), "yyyy-MM-dd"));
        System.out.println(LocalDate.now());
    }
}
