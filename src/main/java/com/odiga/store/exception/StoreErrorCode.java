package com.odiga.store.exception;

import com.odiga.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum StoreErrorCode implements ErrorCode {

    NOT_FOUND_STORE(HttpStatus.NOT_FOUND, "존재하지 않는 가게입니다.");


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
