package com.odiga.owner.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.odiga.global.jwt.JwtTokenDto;
import com.odiga.global.jwt.JwtTokenProvider;
import com.odiga.owner.dao.OwnerRepository;
import com.odiga.owner.dto.OwnerInfoResponseDto;
import com.odiga.owner.dto.OwnerLoginRequestDto;
import com.odiga.owner.dto.OwnerSignupRequestDto;
import com.odiga.owner.entity.Owner;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
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
        owner = Owner.builder()
            .email("example@google.com")
            .password("password")
            .name("name")
            .build();
    }

    @Test
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
    void ownerLoginTest() {
        OwnerLoginRequestDto ownerLoginRequestDto = new OwnerLoginRequestDto("example@google.com", "password");

        when(ownerRepository.findByEmail("example@google.com")).thenReturn(Optional.ofNullable(owner));
        when(passwordEncoder.matches(owner.getPassword(), ownerLoginRequestDto.password())).thenReturn(true);
        when(jwtTokenProvider.createToken("example@google.com")).thenReturn(JwtTokenDto.of("accessToken", "refreshToken"));

        JwtTokenDto tokenDto = ownerAuthService.loginOwner(ownerLoginRequestDto);

        assertThat(tokenDto.accessToken()).isNotNull();
        assertThat(tokenDto.refreshToken()).isNotNull();
    }
}