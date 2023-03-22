package com.example.pmsauth;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.example.auth.config.impl.JwtConfigBase;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.Locale;

@SpringBootTest
class PmsAuthApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private JwtConfigBase jwtConfig;

    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("test", "test");
    }

    @Test
    void testJwt(){
        UserFromTest user = new UserFromTest();
        user.setPassword("114514");
        user.setUsername("Marias");
        user.setCreateDate(new Date(System.currentTimeMillis()));

        String s = JSONUtil.toJsonStr(user);
        String token = jwtConfig.createToken(s, 1);
        System.out.println(token);
    }

    @Test
    void testToken(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjEsInN1YiI6IntcInVzZXJuYW1lXCI6XCJNYXJpYXNcIixcInBhc3N3b3JkXCI6XCIxMTQ1MTRcIixcImNyZWF0ZURhdGVcIjoxNjc5MjIzODc2MjgzfSIsImlhdCI6MTY3OTIyMzg3NiwiZXhwIjoxNjc5MjMxMDc2fQ.DgrWb7AdNTrw01cFBRbm0NkESVQ8Yis2rkwOo_5hdgaGSwICKDLXvST1a5869FBMZLJdghcCfg0mWz9_U-Z8Rg";
        String userFromToken = jwtConfig.getUserFromToken(token);
        UserFromTest u = JSONUtil.toBean(userFromToken, UserFromTest.class);
        Claims tokenClaim = jwtConfig.getTokenClaim(token);
        System.out.println(tokenClaim.getExpiration());
        System.out.println(u.getUsername());
    }

    @Test
    void testIsTokenExpired(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjEsInN1YiI6IntcInVzZXJuYW1lXCI6XCJNYXJpYXNcIixcInBhc3N3b3JkXCI6XCIxMTQ1MTRcIixcImNyZWF0ZURhdGVcIjoxNjc5MjIzODc2MjgzfSIsImlhdCI6MTY3OTIyMzg3NiwiZXhwIjoxNjc5MjMxMDc2fQ.DgrWb7AdNTrw01cFBRbm0NkESVQ8Yis2rkwOo_5hdgaGSwICKDLXvST1a5869FBMZLJdghcCfg0mWz9_U-Z8Rg";
        Claims tokenClaim = jwtConfig.getTokenClaim(token);
        DateTime date = DateUtil.date(tokenClaim.getExpiration());
        System.out.println(date);
    }

    @Test
    void testString(){
        System.out.println("ADMINISTRATION SYSTEM FOR END TO END LUXURY APARTMENT MANAGEMENT SOFTWARE".toLowerCase(Locale.ROOT));
    }
}
