package com.odiga.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final long tokenExpireSeconds;
    private final Key key;
    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(@Value("${security.jwt.secret}") String secretKey,
                            @Value("${security.jwt.expire-seconds}") long tokenExpireSeconds,
                            UserDetailsService userDetailsService) {
        this.tokenExpireSeconds = tokenExpireSeconds;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userDetailsService = userDetailsService;
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

    public Authentication getAuthentication(String token) {
        String email = getClaims(token).getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
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
        } catch (Exception e) {
            // TODO : 추가적인 exception 분기 필요
            throw new RuntimeException(e);
        }
    }
}
