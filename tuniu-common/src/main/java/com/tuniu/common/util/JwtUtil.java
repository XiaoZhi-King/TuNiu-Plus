package com.tuniu.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * JWT 工具类。
 * <p>
 * 密钥可通过系统属性 {@code -Dtuniu.jwt.secret=...} 覆盖，默认值仅用于演示。
 * 使用 HS256，密钥长度需 >= 32 字节。
 */
public final class JwtUtil {

    private JwtUtil() {
    }

    /** 默认密钥（演示用，生产环境请通过 -Dtuniu.jwt.secret 覆盖） */
    private static final String DEFAULT_SECRET =
            "tuniu-microservice-agent-framework-secret-key-2026";

    private static final SecretKey KEY = Keys.hmacShaKeyFor(
            System.getProperty("tuniu.jwt.secret", DEFAULT_SECRET)
                    .getBytes(StandardCharsets.UTF_8));

    /** 默认有效期：24 小时 */
    private static final long EXPIRATION_MS = 24 * 60 * 60 * 1000L;

    /**
     * 生成 token，subject 存放账号
     */
    public static String getToken(String account) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(account)
                .setIssuedAt(new java.util.Date(now))
                .setExpiration(new java.util.Date(now + EXPIRATION_MS))
                .signWith(KEY)
                .compact();
    }

    /**
     * 校验 token 是否合法且未过期
     */
    public static boolean checkToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }
        try {
            Jws<Claims> parsed = Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token);
            return parsed.getBody().getExpiration().after(new java.util.Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从 token 中解析账号
     */
    public static String getAccount(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}
