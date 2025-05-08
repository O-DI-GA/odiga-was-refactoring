package com.odiga.global.exception;

import com.odiga.global.exception.ErrorResponse.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customExceptionHandler(HttpServletRequest request, CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception ex) {
        log.error(ex.getMessage());

        return handleExceptionInternal(GlobalErrorCode.INTERNAL_SERVER);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(makeErrorResponse(ex));
    }

    private ResponseEntity<?> handleExceptionInternal(ErrorCode errorCode) {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .message(errorCode.getMessage())
            .build();

        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }

    private ErrorResponse makeErrorResponse(BindException ex) {
        List<ErrorResponse.ValidationError> errorList = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(ValidationError::of)
            .toList();

        return ErrorResponse.builder()
            .message(GlobalErrorCode.BAD_REQUEST.getMessage())
            .errors(errorList)
            .build();
    }
}
