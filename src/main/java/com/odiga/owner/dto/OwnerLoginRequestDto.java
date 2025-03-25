package com.odiga.owner.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "Owner 로그인 요청")
public record OwnerLoginRequestDto(
    @Schema(description = "Owner 사용자 이메일", example = "example@gmail.com")
    String email,
    @Schema(description = "Owner 사용자 비밀번호", example = "ExamplePassword123@")
    String password) {

}
