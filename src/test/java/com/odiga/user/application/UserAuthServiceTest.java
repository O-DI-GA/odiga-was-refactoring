package com.odiga.user.application;

import com.odiga.global.exception.CustomException;
import com.odiga.global.jwt.JwtTokenDto;
import com.odiga.global.jwt.JwtTokenProvider;
import com.odiga.owner.entity.Owner;
import com.odiga.user.dao.UserRepository;
import com.odiga.user.dto.UserInfoResponseDto;
import com.odiga.user.dto.UserLoginRequestDto;
import com.odiga.user.dto.UserSignupRequestDto;
import com.odiga.user.entity.User;
import com.odiga.user.exception.UserErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        when(jwtTokenProvider.createToken(email)).thenReturn(JwtTokenDto.of("accessToken", "refreshToken"));

        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto(email, "password", "nickname");

        JwtTokenDto jwtTokenDto = userAuthService.signupUser(userSignupRequestDto);

        verify(userRepository, times(1)).save(any(User.class));

        assertThat(jwtTokenDto.accessToken()).isEqualTo("accessToken");
    }

    @Test
    void signupFailTest() {
        String email = "example@gmail.com";

        when(userRepository.existsByEmail(email)).thenReturn(true);

        UserSignupRequestDto userSignupRequestDto = new UserSignupRequestDto(email, "password", "nickname");

        assertThatThrownBy(() -> userAuthService.signupUser(userSignupRequestDto))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", UserErrorCode.EMAIL_CONFLICT);
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