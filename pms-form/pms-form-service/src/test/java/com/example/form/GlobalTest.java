package com.example.form;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        System.out.println(userFromDb);
    }
}
