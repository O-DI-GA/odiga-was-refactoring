package com.odiga.global.jwt;

public record JwtTokenDto(String accessToken, String refreshToken) {

    public static JwtTokenDto of(String accessToken, String refreshToken) {
        return new JwtTokenDto(accessToken, refreshToken);
    }

}
