package com.example.domain.model.exception;

import com.example.domain.model.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

  private final ErrorCode errorCode;
  private final String customMessage;

  public CustomException(ErrorCode errorCode, String customMessage) {
    super(customMessage);
    this.errorCode = errorCode;
    this.customMessage = customMessage;
  }

  public CustomException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
    this.customMessage = errorCode.getMessage();
  }
}
