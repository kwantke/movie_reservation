package com.example.application.dto.response;

import com.example.domain.model.entity.Screening;

import java.time.LocalDate;
import java.time.LocalTime;


public record ScreeningResponseDto (
        LocalDate screeningDate,
        LocalTime startTime,
        LocalTime endTime
){

  public static ScreeningResponseDto fromEntity(Screening screening) {
    return new ScreeningResponseDto(
            screening.getScreeningDate(),
            screening.getStartTime(),
            screening.getEndTime()
    );
  }

}
