package com.odiga.common.exception;

import com.odiga.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum S3ErrorCode implements ErrorCode {

    UNSUPPORTED_FILE(HttpStatus.BAD_REQUEST, "파일이 존재 하지 않거나 지원하지 않는 파일 형식 입니다."),
    FILE_COVERT(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 중 문제가 발생 했습니다.");

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
