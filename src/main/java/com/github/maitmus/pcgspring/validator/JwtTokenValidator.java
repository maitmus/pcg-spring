package com.github.maitmus.pcgspring.validator;

import com.github.maitmus.pcgspring.excpetion.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtTokenValidator {
    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public Claims validateToken(String token) throws UnauthorizedException {
        try {
            // 토큰 파싱 및 검증
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);

            return claimsJws.getBody();
        } catch (Exception ex) {
            throw new UnauthorizedException("Failed to decode JWT: " + ex.getMessage());
        }
    }

    public Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
