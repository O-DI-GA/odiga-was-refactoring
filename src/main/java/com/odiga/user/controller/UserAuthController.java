package com.odiga.user.controller;

import com.odiga.common.dto.ApiResponse;
import com.odiga.user.api.UserAuthApi;
import com.odiga.user.application.UserAuthService;
import com.odiga.user.dto.UserLoginRequestDto;
import com.odiga.user.dto.UserSignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users/auth")
@RequiredArgsConstructor
public class UserAuthController implements UserAuthApi {

    private final UserAuthService userAuthService;

    @PostMapping("signup")
    public ResponseEntity<?> userSignup(@RequestBody UserSignupRequestDto userSignupRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(userAuthService.signupUser(userSignupRequestDto)));
    }

    @PostMapping("login")
    public ResponseEntity<?> userLogin(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        return ResponseEntity.ok(ApiResponse.ok(userAuthService.loginUser(userLoginRequestDto)));
    }

}
