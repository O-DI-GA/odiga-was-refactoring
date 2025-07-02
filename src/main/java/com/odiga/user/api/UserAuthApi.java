package com.odiga.user.api;

import com.odiga.owner.dto.OwnerLoginRequestDto;
import com.odiga.owner.dto.OwnerSignupRequestDto;
import com.odiga.user.dto.UserLoginRequestDto;
import com.odiga.user.dto.UserSignupRequestDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User Auth API", description = "User 회원 관련 API")
public interface UserAuthApi {

    @ApiResponses({
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "회원가입 성공", value = """
                            {
                              "success": true,
                              "data": {
                                "accessToken": "accessTokenExample",
                                "refreshToken": "refreshTokenExample"
                              }
                            }
                            """),})),
            @ApiResponse(responseCode = "409", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "회원가입 실패 - 이미 존재하는 email", value = """
                            {
                              "success": false,
                              "error": {
                                "message": "이미 존재하는 email 입니다."
                              }
                            }
                            """)}))})
    ResponseEntity<?> userSignup(@RequestBody UserSignupRequestDto userSignupRequestDto);

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "로그인 성공", value = """
                            {
                              "success": true,
                              "data": {
                                "accessToken": "accessTokenExample",
                                "refreshToken": "refreshTokenExample"
                              }
                            }
                            """),})),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "로그인 실패 - 존재하지 않는 email", value = """
                            {
                              "success": false,
                              "error": {
                                "message": "존재하지 않는 계정입니다."
                              }
                            }
                            """),})),
            @ApiResponse(responseCode = "401", content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "로그인 실패 - 옳바르지 않은 비밀번호", value = """
                            {
                              "success": false,
                              "error": {
                                "message": "올바르지 않은 비밀번호 입니다."
                              }
                            }
                            """)}))})
    ResponseEntity<?> userLogin(@RequestBody UserLoginRequestDto userLoginRequestDto);
}
