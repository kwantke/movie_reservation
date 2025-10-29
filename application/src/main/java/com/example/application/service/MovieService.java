package com.example.application.service;

import com.example.application.dto.request.MovieRequestDto;
import com.example.application.dto.request.MovieSearchCriteria;
import com.example.application.dto.request.ScreeningRequestDto;
import com.example.application.dto.response.MovieResponseDto;
import com.example.application.port.in.MovieServicePort;
import com.example.application.port.out.MovieRepositoryPort;
import com.example.application.port.out.ScreeningRepositoryPort;
import com.example.application.port.out.TheaterRepositoryPort;
import com.example.domain.model.entity.Movie;
import com.example.domain.model.entity.Screening;
import com.example.domain.model.entity.Theater;
import com.example.domain.model.exception.CustomException;
import com.example.domain.model.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MovieService implements MovieServicePort {

  private final MovieRepositoryPort movieRepositoryPort;
  private final TheaterRepositoryPort theaterRepositoryPort;
  private final ScreeningRepositoryPort screeningRepositoryPort;

  @Cacheable(value = "movies", key = "#movieSearchCriteria.title + '-' + #movieSearchCriteria.genre")
  @Override
  public List<MovieResponseDto> findMovies(MovieSearchCriteria movieSearchCriteria) {

    return movieRepositoryPort.findBy(movieSearchCriteria).stream()
            .map(MovieResponseDto::fromEntity)
            .toList();
  }

  @Override
  public void createMovie(MovieRequestDto movieRequestDto) {
    Movie movie = Movie.of(movieRequestDto.title(),
            movieRequestDto.contentRating(),
            movieRequestDto.releaseDate(),
            movieRequestDto.thumbnailUrl(),
            movieRequestDto.runtimeMinutes(),
            movieRequestDto.genre());

    movieRepositoryPort.save(movie);
  }

  @Override
  public void addScreeningToMovie(Long movieId, ScreeningRequestDto screeningRequestDto) {
    Movie movie = movieRepositoryPort.findById(movieId)
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_MOVIE));

    Theater theater = theaterRepositoryPort.findById(screeningRequestDto.theaterId())
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_THEATER));

    Screening screening = Screening.of(
            screeningRequestDto.startTime(),
            movie,
            theater
    );

    screeningRepositoryPort.save(screening);
  }

}

