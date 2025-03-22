package com.odiga.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customExceptionHandler(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        
        return ResponseEntity.status(errorCode.getHttpStatus())
            .body(ErrorResponse.of(errorCode.getMessage()));
    }

}
