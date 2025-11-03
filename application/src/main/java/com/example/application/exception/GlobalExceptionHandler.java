package com.example.application.exception;

import com.example.domain.exception.CustomException;
import com.example.domain.exception.ErrorCode;
import com.example.domain.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
    log.error("Error occurs {}", ex.toString());
    ErrorResponse errorResponse = new ErrorResponse(
            ex.getErrorCode().name(),
            ex.getCustomMessage(),
            ex.getErrorCode().getHttpStatus().value()
    );

    return ResponseEntity.status(ex.getErrorCode().getHttpStatus().value()).body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
    log.error("Error occurs {}", ex.toString());
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
    log.error("Error occurs {}", ex.toString());
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> {
      errors.put(error.getField(), error.getDefaultMessage());
    });
    return errors;
  }

  @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
  public ResponseEntity<ErrorResponse> handleOptimisticLockFailure(ObjectOptimisticLockingFailureException ex) {
    ErrorResponse errorResponse = new ErrorResponse(
            ErrorCode.OPTIMISTIC_LOCK_CONFLICT.name(),
            ErrorCode.OPTIMISTIC_LOCK_CONFLICT.getMessage(),
            ErrorCode.OPTIMISTIC_LOCK_CONFLICT.getHttpStatus().value()
    );
    return ResponseEntity.status(ErrorCode.OPTIMISTIC_LOCK_CONFLICT.getHttpStatus().value()).body(errorResponse);
  }


}
