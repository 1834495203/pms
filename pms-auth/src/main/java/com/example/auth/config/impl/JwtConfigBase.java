package com.example.auth.config.impl;

import cn.hutool.core.date.DateUtil;
import com.example.auth.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * 基於JWT自身
 */
@Component(value = "JwtConfigBase")
public class JwtConfigBase extends JwtConfig {

    public String createToken (String subject, Integer id){
        Date nowDate = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, expire);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("userId", id)
                .setSubject(subject)
                .setIssuedAt(nowDate)
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String createToken (String subject, Integer id, String auth){
        Date nowDate = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, expire);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("id", id)
                .claim("auth", auth)
                .setSubject(subject)
                .setIssuedAt(nowDate)
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public String getAuth(String token) {
        Claims tokenClaim = this.getTokenClaim(token);
        return tokenClaim.get("auth", String.class);
    }

    public boolean isTokenExpired (String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getTokenClaim(token).getExpiration();
    }

    public String getUserFromToken(String token) {
        return getTokenClaim(token).getSubject();
    }
}
