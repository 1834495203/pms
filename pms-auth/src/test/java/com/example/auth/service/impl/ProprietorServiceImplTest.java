package com.example.auth.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GLaDOS
 * @date 2023/4/15 16:34
 */
@SpringBootTest
class ProprietorServiceImplTest {

    @Test
    void registerForProprietor() {
        String s = DigestUtil.md5Hex("");
        System.out.println(s);
    }
}