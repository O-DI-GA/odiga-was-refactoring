package com.odiga.global.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum GlobalErrorCode implements ErrorCode {

    INTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류 입니다.");

    private final HttpStatus httpStatus;
    private final String message;


    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
