package com.example.domain.model.exception;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        String errorCode,
        String message,
        int statusCode
) {
}
