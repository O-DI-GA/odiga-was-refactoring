package com.odiga.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odiga.global.exception.CustomException;
import com.odiga.global.exception.ErrorCode;
import com.odiga.global.exception.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            sendErrorResponse(response, e.getErrorCode());
        }
    }

    private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode)
        throws IOException {

        ErrorResponse errorResponse = ErrorResponse.builder()
            .message(errorCode.getMessage())
            .build();

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getHttpStatus().value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
