package com.example.form;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
}
