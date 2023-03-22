package com.example.auth.config;

import com.example.exception.Error;
import com.example.exception.PMSException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.util.Date;

@Data
@Slf4j
@PropertySource(value = "classpath:bootstrap.yml")
public abstract class JwtConfig {

    @Value("${config.jwt.secret}")
    protected String secret;

    @Value("${config.jwt.expire}")
    protected int expire;

    @Value("${config.jwt.header}")
    protected String header;

    /**
     * 生成token
     * @param subject 保存的用戶信息
     * @param id 用户id
     * @return token
     */
    public abstract String createToken (String subject, Integer id);

    /**
     * 创建时写入权限
     * @param subject 用户信息
     * @param id 用户id
     * @param auth 权限
     * @return token
     */
    public abstract String createToken (String subject, Integer id, String auth);

    public abstract String getAuth (String token);

    /**
     * 获取token中注册信息
     * @param token token
     * @return claims
     */
    public Claims getTokenClaim (String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            throw new PMSException("获取token内信息错误, 抛出异常: "+e.getMessage(),
                    Error.TOKEN_LOAD_FAILED);
        }
    }

    /**
     * 验证token是否过期失效
     * @return boolean
     */
    public abstract boolean isTokenExpired (String token);

    /**
     * 获取token失效时间
     * @param token token
     * @return token失效时间
     */
    public abstract Date getExpirationDateFromToken(String token);

    /**
     * 获取用户从token中
     */
    public abstract String getUserFromToken(String token);
}
