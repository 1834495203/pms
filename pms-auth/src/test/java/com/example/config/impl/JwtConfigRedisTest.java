package com.example.config.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.example.auth.config.impl.JwtConfigRedis;
import com.example.pmsauth.UserFromTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class JwtConfigRedisTest {

    @Autowired
    private JwtConfigRedis jwtConfigRedis;

    @Test
    void createToken() {
        UserFromTest user = new UserFromTest();
        user.setUsername("Marias");
        user.setPassword("114514");
        user.setCreateDate(new Date(System.currentTimeMillis()));

        String s = JSONUtil.toJsonStr(user);
        System.out.println(jwtConfigRedis.createToken(s, 1));
    }

    @Test
    void isTokenExpired() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjEsInN1YiI6IntcInVzZXJuYW1lXCI6XCJNYXJpYXNcIixcInBhc3N3b3JkXCI6XCIxMTQ1MTRcIixcImNyZWF0ZURhdGVcIjoxNjc5MjI1NDc4NDk4fSJ9.pNv346t-RVvKO7rKneaHEMDyYuHevJJuF-zpElkLLf0Wxyk54BqaA8auTaj9qTNUvrfFXRBi9GKJ9vxzMbjyaw";
        DateTime date = DateUtil.date(jwtConfigRedis.getExpirationDateFromToken(token));
        System.out.println(date);
    }

    @Test
    void getExpirationDateFromToken() {
    }

    @Test
    void getUserFromToken() {
    }
}