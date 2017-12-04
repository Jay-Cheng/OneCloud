package com.zhengzijie.onecloud.manager;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * 用于生成和解析JSON Web Token
 */
public class JWTUtil {
    
    private static Key key = MacProvider.generateKey();
    
    public static String generateToken(String username) {
        long expired = System.currentTimeMillis() + 1000 * 60 * 30; // 30分钟后过期
        String jwt = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(expired))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return jwt;
    }
    
    /**
     * 解析JWT
     * @return 持有改token的username
     */
    public static String parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
