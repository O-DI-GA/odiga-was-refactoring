package com.odiga.owner.controller;

import com.odiga.owner.application.OwnerAuthService;
import com.odiga.owner.dto.OwnerLoginRequestDto;
import com.odiga.owner.dto.OwnerSignupRequestDto;
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
public class OwnerAuthController {

    private final OwnerAuthService ownerAuthService;

    @PostMapping("signup")
    public ResponseEntity<?> ownerSignup(@RequestBody OwnerSignupRequestDto ownerSignupRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ownerAuthService.signupOwner(ownerSignupRequestDto));
    }


    @PostMapping("login")
    public ResponseEntity<?> ownerLogin(@RequestBody OwnerLoginRequestDto ownerLoginRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(ownerAuthService.loginOwner(ownerLoginRequestDto));
    }

}
