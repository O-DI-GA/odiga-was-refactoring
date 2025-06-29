package com.odiga.user.dto;

import com.odiga.user.entity.User;

public record UserInfoResponseDto(String email, String nickname, String profileImageUrl) {

    public static UserInfoResponseDto from(User user) {
        return new UserInfoResponseDto(user.getEmail(), user.getNickname(), user.getProfileImageUrl());
    }
}
