package com.odiga.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.odiga.global.exception.ErrorResponse;

public record ApiResponse<T>(
    boolean success,
    @JsonInclude(Include.NON_NULL)
    T data,
    @JsonInclude(Include.NON_NULL)
    ErrorResponse error
) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, data, null);
    }

    public static <T> ApiResponse<T> fail(ErrorResponse error) {
        return new ApiResponse<>(false, null, error);
    }

}
