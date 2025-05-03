package com.github.maitmus.pcgspring.token.service;

import com.github.maitmus.pcgspring.common.constant.TokenType;
import com.github.maitmus.pcgspring.user.v1.entity.User;
import com.github.maitmus.pcgspring.validator.JwtTokenValidator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenService {
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 300 * 1000L;
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 3600 * 1000 * 24 * 7L;
    private final JwtTokenValidator jwtTokenValidator;

    public String generateToken(User user, TokenType type) {
        Map<String, Object> claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("name", user.getName());
        claims.put("nickname", user.getNickname());
        claims.put("email", user.getEmail());
        claims.put("birth", user.getBirth().toString());
        claims.put("username", user.getUsername());
        claims.put("roles", user.getRoles());
        claims.put("status", user.getStatus());
        claims.put("createdAt", user.getCreatedAt().toString());
        claims.put("updatedAt", user.getUpdatedAt().toString());

        Date now = new Date();

        Date expiryDate;

        if (type.equals(TokenType.ACCESS)) {
            expiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_TIME);
        } else if (type.equals(TokenType.REFRESH)) {
            expiryDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_TIME);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .signWith(jwtTokenValidator.getSigningKey(), SignatureAlgorithm.HS256)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .compact();
    }
}

