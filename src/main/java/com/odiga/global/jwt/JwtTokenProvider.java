package com.odiga.global.jwt;

import com.odiga.global.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final long tokenExpireSeconds;
    private final Key key;

    public JwtTokenProvider(@Value("${security.jwt.secret}") String secretKey,
                            @Value("${security.jwt.expire-seconds}") long tokenExpireSeconds) {
        this.tokenExpireSeconds = tokenExpireSeconds;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtTokenDto createToken(String email) {
        long now = (new Date()).getTime();
        Date accessTokenExpireTime = new Date(now + tokenExpireSeconds);
        Date refreshTokenExpireTime = new Date(now + (tokenExpireSeconds * 2 * 30));

        String accessToken = Jwts.builder()
            .setSubject(email)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(accessTokenExpireTime)
            .compact();

        String refreshToken = Jwts.builder()
            .setSubject(email)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(refreshTokenExpireTime)
            .compact();

        return JwtTokenDto.of(accessToken, refreshToken);
    }

    public Claims getClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException e) {
            throw new CustomException(JwtErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new CustomException(JwtErrorCode.EXPIRE_ERROR);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(JwtErrorCode.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new CustomException(JwtErrorCode.NOT_FOUND_TOKEN);
        }
    }
}
