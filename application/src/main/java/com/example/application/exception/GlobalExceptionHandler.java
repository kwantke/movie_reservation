package com.example.application.exception;

import com.example.domain.model.exception.CustomException;
import com.example.domain.model.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
            ex.getErrorCode().name(),
            ex.getCustomMessage(),
            ex.getErrorCode().getHttpStatus().value()
    );

    return ResponseEntity.status(ex.getErrorCode().getHttpStatus().value()).body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
    ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.name(), // 500
            ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value()
    );
    return ResponseEntity.status(500).body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
  public java.util.Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> {
      errors.put(error.getField(), error.getDefaultMessage());
    });
    return errors;
  }


}
