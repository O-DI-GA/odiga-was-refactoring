package com.odiga.owner.exception;

import com.odiga.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum OwnerErrorCode implements ErrorCode {

    EMAIL_CONFLICT(HttpStatus.CONFLICT, "이미 존재하는 email 입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 계정입니다."),
    INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "올바르지 않은 비밀번호 입니다.");

    private final HttpStatus httpStatus;
    private final String message;


    @Override
    public HttpStatus getHttpStatus() {
        return null;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
