package com.example.domain.model.validation;

import com.example.domain.model.exception.CustomException;
import com.example.domain.model.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Component
public class MovieValidation {

  private static final int MAX_TITLE_BYTE_LENGTH = 150; // DB에 설정한 BYTE 기준
  private static final Charset CHARSET = StandardCharsets.UTF_8; // 운영체제마다 기본 인코딩이 다를 수 있으므로 바이트 계산의 기준을 명시

  public void validateTitleLength(String title) {
    if (title == null) { // title이 null 이면 검증 로직을 실행하지 않음
      return;
    }

    int byteLength = title.getBytes(CHARSET).length;
    if (byteLength > MAX_TITLE_BYTE_LENGTH) {
      throw new CustomException(ErrorCode.INVALID_TITME_LEGHTH);
    }
  }
}
