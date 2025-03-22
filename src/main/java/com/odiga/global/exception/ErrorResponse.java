package com.odiga.global.exception;

public record ErrorResponse(String message) {


    public static ErrorResponse of(String message) {
        return new ErrorResponse(message);
    }
}
