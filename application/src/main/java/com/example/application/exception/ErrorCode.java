package com.example.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
  // 영화 정보 관련 에러
  INVALID_TITME_LEGHTH("Movie title cannot be more than 50 characters.", HttpStatus.BAD_REQUEST),
  INVALID_CONTENT_RATING( "Content rating must be provided.", HttpStatus.BAD_REQUEST),
  INVALID_GENRE("Genre cannot be empty.", HttpStatus.BAD_REQUEST),
  INVALID_MOVIE("Movie must be provided.", HttpStatus.BAD_REQUEST),
  INVALID_THEATER("Theater must be provided.", HttpStatus.BAD_REQUEST),

  // 예약 관련 에러
  INVALID_SCREENING("Screening not found.", HttpStatus.BAD_REQUEST),
  INVALID_MEMBER("Member not found.", HttpStatus.BAD_REQUEST),
  MAX_SEATS_EXCEEDED("Cannot reserve more than 5 seats per screening, including previous reservations.", HttpStatus.BAD_REQUEST),
  SEAT_ALREADY_RESERVED("Some seats are already reserved.", HttpStatus.BAD_REQUEST),
  SEATS_NOT_CONSECUTIVE("Seats must be consecutive in the same row.", HttpStatus.BAD_REQUEST),
  INVALID_REQUEST("Invalid request.", HttpStatus.BAD_REQUEST),
  RESERVATION_REQUEST_FAILED("Reservation request failed.", HttpStatus.BAD_REQUEST),
  ;

  private final String message;
  private final HttpStatus httpStatus;

}
