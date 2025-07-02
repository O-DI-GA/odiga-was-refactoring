package com.odiga.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserSignupRequestDto(
        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "이메일 형식에 맞지 않습니다.")
        @Schema(description = "User 사용자 이메일", example = "example@gmail.com")
        String email,
        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Schema(description = "User 사용자 비밀번호", example = "ExamplePassword123@")
        String password,
        @NotBlank(message = "닉네임은 필수 입력값입니다.")
        @Schema(description = "User 사용자 이름", example = "홍길동")
        String nickname
) {

}
