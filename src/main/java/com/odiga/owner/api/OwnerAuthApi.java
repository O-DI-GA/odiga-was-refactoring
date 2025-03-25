package com.odiga.owner.api;

import com.odiga.owner.dto.OwnerLoginRequestDto;
import com.odiga.owner.dto.OwnerSignupRequestDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Owner Auth API", description = "Owner 회원 관련 API")
public interface OwnerAuthApi {

    @ApiResponses({
        @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "회원가입 성공", value = """
                {
                  "id": 1,
                  "email": "example@gmail.com",
                  "name": "홍길동"
                }
                """),})),
        @ApiResponse(responseCode = "409", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "회원가입 실패 - 이미 존재하는 email", value = """
                {
                  "message": "이미 존재하는 email 입니다."
                }
                """)}))})
    ResponseEntity<?> ownerSignup(@RequestBody OwnerSignupRequestDto ownerSignupRequestDto);

    @ApiResponses({
        @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "로그인 성공", value = """
                {
                  "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJleGFtcGxlQGdtYWlsLmNvbSIsImV4cCI6MTc0MjkxNDQ5Mn0.xBpNreQDdR_595Ap1oTDmUmJXCKB-HATRyvtUSUC-jwQVcI-ZmoG9wpi8y4xJOzIi00m2yKIkvsmNzkOeOf9DQ",
                  "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJleGFtcGxlQGdtYWlsLmNvbSIsImV4cCI6MTc0MjkxNzA0MH0.CTP03FkIwQ53jyBezXEM4pxtQoiBtoglW-3Da0q65xoa-Qzjvsqw5q9Pp31e2SJ_xtEzbkCLOh6qqkpBy0BakQ"
                }
                """),})),
        @ApiResponse(responseCode = "201", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "로그인 실패 - 존재하지 않는 email", value = """
                {
                  "message": "존재하지 않는 계정입니다."
                }
                """),})),
        @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "로그인 실패 - 옳바르지 않은 비밀번호", value = """
                {
                  "message": "올바르지 않은 비밀번호 입니다."
                }
                """)}))})
    ResponseEntity<?> ownerLogin(@RequestBody OwnerLoginRequestDto ownerLoginRequestDto);

}
