package com.odiga.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customExceptionHandler(HttpServletRequest request, CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(ErrorResponse.of(errorCode.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception ex) {
        return ResponseEntity.status(GlobalErrorCode.INTERNAL_SERVER.getHttpStatus())
            .body(ErrorResponse.of(GlobalErrorCode.INTERNAL_SERVER.getMessage()));
    }
}
