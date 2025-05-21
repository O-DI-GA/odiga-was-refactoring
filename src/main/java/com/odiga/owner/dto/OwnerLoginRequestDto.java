package com.odiga.owner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(title = "Owner 로그인 요청")
public record OwnerLoginRequestDto(

    @NotBlank(message = "이메일은 필수입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "이메일 형식에 맞지 않습니다.")
    @Schema(description = "Owner 사용자 이메일", example = "example@gmail.com")
    String email,
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Schema(description = "Owner 사용자 비밀번호", example = "ExamplePassword123@")
    String password) {

}
