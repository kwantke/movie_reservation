package com.example.application.dto.response;

import com.example.domain.model.entity.Movie;

import java.time.LocalDate;

public record MovieResponseDto(
        String title,
        String contentRating,
        LocalDate releaseDate,
        String thumbnailUrl,
        int runtimeMinutes,
        String genre,
        String theater

) {


  public static MovieResponseDto fromEntity(Movie movie) {
    return null;
  }
}
