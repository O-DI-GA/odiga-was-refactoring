package com.odiga.reservation.exception;

import com.odiga.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ReservationSlotErrorCode implements ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 예약 가능 슬롯 입니다.");

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
