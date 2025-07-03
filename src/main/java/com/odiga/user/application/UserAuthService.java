package com.odiga.user.application;

import com.odiga.global.exception.CustomException;
import com.odiga.global.jwt.JwtTokenDto;
import com.odiga.global.jwt.JwtTokenProvider;
import com.odiga.user.dao.UserRepository;
import com.odiga.user.dto.UserLoginRequestDto;
import com.odiga.user.dto.UserSignupRequestDto;
import com.odiga.user.entity.User;
import com.odiga.user.exception.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public JwtTokenDto signupUser(UserSignupRequestDto userSignupRequestDto) {

        if (userRepository.existsByEmail(userSignupRequestDto.email())) {
            throw new CustomException(UserErrorCode.EMAIL_CONFLICT);
        }

        User user = User.builder()
            .email(userSignupRequestDto.email())
            .password(passwordEncoder.encode(userSignupRequestDto.password()))
            .nickname(userSignupRequestDto.nickname())
            .build();

        userRepository.save(user);

        return jwtTokenProvider.createToken(user.getEmail());
    }

    @Transactional(readOnly = true)
    public JwtTokenDto loginUser(UserLoginRequestDto userLoginRequestDto) {
        User user = userRepository.findByEmail(userLoginRequestDto.email())
            .orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND));

        if (!passwordEncoder.matches(userLoginRequestDto.password(), user.getPassword())) {
            throw new CustomException(UserErrorCode.INCORRECT_PASSWORD);
        }
        
        return jwtTokenProvider.createToken(user.getEmail());
    }

}
