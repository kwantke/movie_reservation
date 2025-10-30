package com.example.domain.model.valueObject;

import com.example.domain.exception.CustomException;
import com.example.domain.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ContentRating {

  ALL("전체 관람가", 0, "A"),
  TWELVE("12세 이상 관람가", 12, "B"),
  FIFTEEN("15세 이상 관람가", 15, "C"),
  ADULT("12세 이상 관람가", 18, "D")
  ;

  private final String description;
  private final int ageLimit;
  private final String dbValue;

  /*
  MAP = {
    "A" -> ContentRating.ALL,
    "B" -> ContentRating.TWELVE,
    "C" -> ContentRating.FIFTEEN,
    "D" -> ContentRating.ADULT
  }
  */
  private static final Map<String, ContentRating> MAP =
          Stream.of(values()).collect(Collectors.toMap(ContentRating::getDbValue, contentRating -> contentRating));

  public static ContentRating fromDbValue(String dbValue) {
    ContentRating contentRating = MAP.get(dbValue);
    if (dbValue == null) {
      throw new CustomException(ErrorCode.INVALID_CONTENT_RATING);
    }
    return contentRating;
  }
}
