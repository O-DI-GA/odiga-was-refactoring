package com.odiga.store.exception;

import com.odiga.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum OwnerStoreErrorCode implements ErrorCode {

    FORBIDDEN(HttpStatus.FORBIDDEN, "해당 가게에 접근 권한이 없습니다."),
    ALREADY_OPEN(HttpStatus.BAD_REQUEST, "이미 가게가 영업중 입니다."),
    ALREADY_CLOSE(HttpStatus.BAD_REQUEST, "이미 가게가 영업 종료입니다.");


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
