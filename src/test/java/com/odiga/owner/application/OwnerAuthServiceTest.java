package com.odiga.owner.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.odiga.global.exception.CustomException;
import com.odiga.global.jwt.JwtTokenDto;
import com.odiga.global.jwt.JwtTokenProvider;
import com.odiga.owner.dao.OwnerRepository;
import com.odiga.owner.dto.OwnerInfoResponseDto;
import com.odiga.owner.dto.OwnerLoginRequestDto;
import com.odiga.owner.dto.OwnerSignupRequestDto;
import com.odiga.owner.entity.Owner;
import com.odiga.owner.exception.OwnerErrorCode;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class OwnerAuthServiceTest {

    @InjectMocks
    OwnerAuthService ownerAuthService;

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    Owner owner;


    @BeforeEach
    void init() {
        owner = Owner.builder().email("example@google.com").password("password").name("name")
            .build();
    }

    @Test
    @DisplayName("Owner 회원가입 테스트")
    void ownerSignupTest() {
        when(ownerRepository.existsByEmail("example@google.com")).thenReturn(false);
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);
        when(passwordEncoder.encode("password")).thenReturn("password");

        OwnerSignupRequestDto ownerSignupRequestDto = new OwnerSignupRequestDto("example@google.com", "password", "name");
        OwnerInfoResponseDto ownerInfoResponseDto = ownerAuthService.signupOwner(ownerSignupRequestDto);

        verify(ownerRepository, times(1)).save(any(Owner.class));

        assertThat(ownerInfoResponseDto.email()).isEqualTo(owner.getUsername());
        assertThat(ownerInfoResponseDto.name()).isEqualTo(owner.getName());
    }

    @Test
    @DisplayName("Owner 회원가입 실패 테스트 - 이메일 중복")
    void ownerSingUpConflictEmailExceptionTest() {
        String existEmail = "example@google.com";

        when(ownerRepository.existsByEmail(existEmail)).thenReturn(true);
        OwnerSignupRequestDto ownerSignupRequestDto = new OwnerSignupRequestDto(existEmail, "password", "name");

        assertThatThrownBy(() -> ownerAuthService.signupOwner(ownerSignupRequestDto))
            .isInstanceOf(CustomException.class)
            .hasFieldOrPropertyWithValue("errorCode", OwnerErrorCode.EMAIL_CONFLICT);

    }

    @Test
    @DisplayName("Owner 로그인 테스트")
    void ownerLoginTest() {
        OwnerLoginRequestDto ownerLoginRequestDto = new OwnerLoginRequestDto("example@google.com", "password");

        when(ownerRepository.findByEmail("example@google.com")).thenReturn(Optional.ofNullable(owner));
        when(passwordEncoder.matches(owner.getPassword(), ownerLoginRequestDto.password())).thenReturn(true);
        when(jwtTokenProvider.createToken("example@google.com")).thenReturn(JwtTokenDto.of("accessToken", "refreshToken"));

        JwtTokenDto tokenDto = ownerAuthService.loginOwner(ownerLoginRequestDto);

        assertThat(tokenDto.accessToken()).isNotNull();
        assertThat(tokenDto.refreshToken()).isNotNull();
    }

    @Test
    @DisplayName("Owner 로그인 실패 테스트 - 이메일 존재 x")
    void ownerLoginNotFoundExceptionTest() {
        String email = "example@google.com";
        when(ownerRepository.findByEmail(email)).thenReturn(Optional.empty());

        OwnerLoginRequestDto ownerLoginRequestDto = new OwnerLoginRequestDto(email, "password");

        assertThatThrownBy(() -> ownerAuthService.loginOwner(ownerLoginRequestDto))
            .isInstanceOf(CustomException.class)
            .hasFieldOrPropertyWithValue("errorCode", OwnerErrorCode.NOT_FOUND);
    }

    @Test
    @DisplayName("Owner 로그인 실패 - 비밀번호 불일치")
    void ownerLoginInCorrectPasswordExceptionTest() {
        String email = "example@google.com";
        String inCorrectPassword = "inCorrectPassword";

        when(ownerRepository.findByEmail(email)).thenReturn(Optional.ofNullable(owner));
        when(passwordEncoder.matches(inCorrectPassword, owner.getPassword())).thenReturn(false);

        OwnerLoginRequestDto ownerLoginRequestDto = new OwnerLoginRequestDto(email, inCorrectPassword);

        assertThatThrownBy(() -> ownerAuthService.loginOwner(ownerLoginRequestDto))
            .isInstanceOf(CustomException.class)
            .hasFieldOrPropertyWithValue("errorCode", OwnerErrorCode.INCORRECT_PASSWORD);
    }


}