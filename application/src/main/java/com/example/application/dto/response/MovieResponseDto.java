package com.example.application.dto.response;

import com.example.domain.model.entity.Movie;
import com.example.domain.model.projection.MoviePorjection;

import java.time.LocalDate;
import java.util.List;

// record를 사용한 불변 데이터 클래스
public record MovieResponseDto(
        Long id,
        String title,
        String contentRating,
        LocalDate releaseDate,
        String thumbnailUrl,
        int runtimeMinutes,
        String genre,
        List<ScreeningResponseDto> screenings

) {

  // 엔티티를 dto로 변환하는 팩토리 메서드 추가
  public static MovieResponseDto fromEntity(Movie movie) {
    return new MovieResponseDto(
            movie.getId(),
            movie.getTitle(),
            movie.getContentRating().name(),
            movie.getReleaseDate(),
            movie.getThumbnailUrl(),
            movie.getRuntimeMinutes(),
            movie.getGenre().name(),
            movie.getScreenings().stream().map(ScreeningResponseDto::fromEntity).toList()
    );
  }

  public static MovieResponseDto fromProjection(MoviePorjection movie) {
    return new MovieResponseDto(
            movie.getId(),
            movie.getTitle(),
            movie.getContentRating(),
            movie.getReleaseDate(),
            movie.getThumbnailUrl(),
            movie.getRuntimeMinutes(),
            movie.getGenre(),
            movie.getScreenings().stream().map(ScreeningResponseDto::fromProjection).toList()
    );
  }
}
