package com.smbc.library.auth_service.utils;

import java.util.Date;
import java.util.HashMap;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {

    @Value("${smbc.properties.data.expire-token-ms:28800000}")
    private long EXPIRE_TIME;

    @Value("${smbc.properties.data.secret-key}")
    private String SECRET_KEY;

    public Object claimValue(String token, String key) {
        try {
            if (token == null || token == "")
                return null;

            SecretKey secretKey = getSignedKey();
            Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();

            return claims != null ? claims.get(key) : null;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public String extractUsername(String token) {
        SecretKey key = getSignedKey();
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public String generateToken(String username, HashMap<String, Object> claimData) {
        HashMap<String, Object> wrappedClaim = new HashMap<>();
        wrappedClaim.put("userLogin", claimData);
        SecretKey key = getSignedKey();

        return Jwts.builder()
                .subject(username)
                .claims(wrappedClaim)
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getSignedKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
