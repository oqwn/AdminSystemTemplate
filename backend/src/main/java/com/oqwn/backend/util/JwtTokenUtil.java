package com.oqwn.backend.util;

import com.oqwn.backend.model.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.Jwts.SIG.HS512;

@Component
public class JwtTokenUtil {

    // extension: using jwe instead

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 48;
    private static final SecretKey secretKey = Jwts.SIG.HS512.key().build();

    // 生成JWT令牌
    public String generateToken(User user) {

        return Jwts.builder()
                .subject(String.valueOf(user.getId()))  // 将用户名作为JWT的主体
                .issuedAt(new Date())  // 设置签发时间
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // 设置过期时间
                .signWith(secretKey, HS512)  // 使用HS512算法进行签名
                .compact();
    }

    // 从JWT中提取用户名
    public Long getIDFromToken(String token) {
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();

        Jws<Claims> claims = jwtParser.parseSignedClaims(token);
        return Long.valueOf(claims.getPayload().getSubject());
    }

    // 验证JWT是否有效
    public boolean validateToken(String token, User user) {
        return (getIDFromToken(token).equals(user.getId()) && !isTokenExpired(token));
    }

    // 判断JWT是否过期
    private boolean isTokenExpired(String token) {
        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();

        Jws<Claims> claims = jwtParser.parseSignedClaims(token);
        Date expiration = claims.getPayload().getExpiration();
        return expiration.before(new Date());
    }
}
