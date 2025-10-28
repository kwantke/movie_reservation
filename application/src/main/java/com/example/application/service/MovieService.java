package com.example.application.service;

import com.example.application.dto.request.MovieRequestDto;
import com.example.application.dto.request.MovieSearchCriteria;
import com.example.application.dto.response.MovieResponseDto;
import com.example.application.port.in.MovieServicePort;
import com.example.application.port.out.MovieRepositoryPort;
import com.example.domain.model.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MovieService implements MovieServicePort {

  private final MovieRepositoryPort movieRepositoryPort;

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
}

