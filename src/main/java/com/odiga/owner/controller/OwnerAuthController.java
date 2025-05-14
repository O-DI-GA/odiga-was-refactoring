package com.odiga.owner.controller;

import com.odiga.common.dto.ApiResponse;
import com.odiga.owner.api.OwnerAuthApi;
import com.odiga.owner.application.OwnerAuthService;
import com.odiga.owner.dto.OwnerLoginRequestDto;
import com.odiga.owner.dto.OwnerSignupRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/owners/auth")
public class OwnerAuthController implements OwnerAuthApi {

    private final OwnerAuthService ownerAuthService;

    @PostMapping("signup")
    public ResponseEntity<?> ownerSignup(@Valid @RequestBody OwnerSignupRequestDto ownerSignupRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.ok(ownerAuthService.signupOwner(ownerSignupRequestDto)));
    }


    @PostMapping("login")
    public ResponseEntity<?> ownerLogin(@Valid @RequestBody OwnerLoginRequestDto ownerLoginRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ApiResponse.ok(ownerAuthService.loginOwner(ownerLoginRequestDto)));
    }

}
