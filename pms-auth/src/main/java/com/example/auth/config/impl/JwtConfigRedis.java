package com.example.auth.config.impl;

import cn.hutool.core.date.DateUtil;
import com.example.auth.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 基於redis的JWT
 */
@Configuration("JwtConfigRedis")
public class JwtConfigRedis extends JwtConfig {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String PREFIX = "token:";

    @Override
    public String createToken(String subject, Integer id) {
        //将token存入redis
        redisTemplate.opsForValue().set(PREFIX+id, subject, expire, TimeUnit.HOURS);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("userId", id)
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public String createToken(String subject, Integer id, String auth) {
        //将token存入redis
        redisTemplate.opsForValue().set(PREFIX+id, subject, expire, TimeUnit.HOURS);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("id", id)
                .claim("auth", auth)
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public String getAuth(String token) {
        return this.getTokenClaim(token).get("auth", String.class);
    }

    @Override
    public boolean isTokenExpired(String token) {
        Claims tokenClaim = this.getTokenClaim(token);
        Integer userId = tokenClaim.get("userId", Integer.class);
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + userId));
    }

    @Override
    public Date getExpirationDateFromToken(String token) {
        Long expire;
        Integer id = this.getTokenClaim(token).get("userId", Integer.class);
        if ((expire = redisTemplate.getExpire(PREFIX + id)) == null)
            return null;
        return DateUtil.offsetSecond(new Date(System.currentTimeMillis()), Math.toIntExact(expire));
    }

    @Override
    public String getUserFromToken(String token) {
        if (!isTokenExpired(token)){
            return this.getTokenClaim(token).getSubject();
        }
        return null;
    }
}
