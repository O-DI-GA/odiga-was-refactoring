package com.odiga.user.application;

import com.odiga.global.jwt.JwtTokenDto;
import com.odiga.global.jwt.JwtTokenProvider;
import com.odiga.user.dao.UserRepository;
import com.odiga.user.dto.UserInfoResponseDto;
import com.odiga.user.dto.UserLoginRequestDto;
import com.odiga.user.dto.UserSignupRequestDto;
import com.odiga.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAuthServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    UserAuthService userAuthService;

    @Test
    void signupTest() {
        String email = "example@gmail.com";
        when(userRepository.existsByEmail(email)).thenReturn(false);

        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto(email, "password", "nickname");

        UserInfoResponseDto userInfoResponseDto = userAuthService.signupUser(userSignupRequestDto);

        assertThat(userInfoResponseDto.email()).isEqualTo(email);
    }

    @Test
    void loginTest() {
        String email = "example@gmail.com";
        String password = "password";

        User user = User.builder()
                .email(email)
                .password(password)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.ofNullable(user));
        when(jwtTokenProvider.createToken(email)).thenReturn(JwtTokenDto.of("accessToken", "refreshToken"));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        JwtTokenDto jwtTokenDto = userAuthService.loginUser(new UserLoginRequestDto(email, password));

        assertThat(jwtTokenDto.accessToken()).isEqualTo("accessToken");
    }
}