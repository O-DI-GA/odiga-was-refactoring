package com.odiga.owner.application;

import com.odiga.global.exception.CustomException;
import com.odiga.global.jwt.JwtTokenDto;
import com.odiga.global.jwt.JwtTokenProvider;
import com.odiga.owner.dao.OwnerRepository;
import com.odiga.owner.dto.OwnerInfoResponseDto;
import com.odiga.owner.dto.OwnerLoginRequestDto;
import com.odiga.owner.dto.OwnerSignupRequestDto;
import com.odiga.owner.entity.Owner;
import com.odiga.owner.exception.OwnerErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerAuthService {

    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public OwnerInfoResponseDto signupOwner(OwnerSignupRequestDto ownerSignupRequestDto) {
        if (ownerRepository.existsByEmail(ownerSignupRequestDto.email())) {
            throw new CustomException(OwnerErrorCode.EMAIL_CONFLICT);
        }

        Owner owner = Owner.builder()
            .name(ownerSignupRequestDto.name())
            .email(ownerSignupRequestDto.email())
            .password(passwordEncoder.encode(ownerSignupRequestDto.password()))
            .build();

        ownerRepository.save(owner);

        return OwnerInfoResponseDto.from(owner);
    }

    @Transactional(readOnly = true)
    public JwtTokenDto loginOwner(OwnerLoginRequestDto ownerLoginRequestDto) {

        Owner owner = ownerRepository.findByEmail(ownerLoginRequestDto.email())
            .orElseThrow(() -> new CustomException(OwnerErrorCode.NOT_FOUND));

        if (!passwordEncoder.matches(ownerLoginRequestDto.password(), owner.getPassword())) {
            throw new CustomException(OwnerErrorCode.INCORRECT_PASSWORD);
        }

        return jwtTokenProvider.createToken(owner.getEmail());
    }

}
