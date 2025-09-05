package com.odiga.global.exception;

import com.odiga.common.dto.ApiResponse;
import com.odiga.global.exception.ErrorResponse.ValidationError;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customExceptionHandler(HttpServletRequest request, CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exceptionHandler(Exception ex, HttpServletRequest request) {
        log.error("처리되지 않은 에러 : {} {}", request.getRequestURI(), request.getMethod(), ex);
        return handleExceptionInternal(GlobalErrorCode.INTERNAL_SERVER);
    }

    private ResponseEntity<?> handleExceptionInternal(ErrorCode errorCode) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.status(errorCode.getHttpStatus()).body(ApiResponse.fail(errorResponse));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        return ResponseEntity.status(GlobalErrorCode.BAD_REQUEST.getHttpStatus())
                .body(ApiResponse.fail(makeErrorResponse(ex)));
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
