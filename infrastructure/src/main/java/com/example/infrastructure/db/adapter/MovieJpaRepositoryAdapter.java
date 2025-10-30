package com.example.infrastructure.db.adapter;

import com.example.application.dto.request.MovieSearchCriteria;
import com.example.application.port.out.MovieRepositoryPort;
import com.example.domain.model.entity.Movie;
import com.example.infrastructure.db.MovieJpaRepository;
import com.example.infrastructure.db.ScreeningJpaRepository;
import com.example.infrastructure.db.querydsl.MovieRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MovieJpaRepositoryAdapter implements MovieRepositoryPort {

  private final MovieJpaRepository movieJpaRepository;
  private final MovieRepositoryCustom movieRepositoryCustom;

  @Override
  public List<Movie> findMovies() {
    return movieJpaRepository.findAll();
  }

  @Override
  public void save(Movie movie) {
    movieJpaRepository.save(movie);
  }

  @Override
  public List<Movie> findBy(MovieSearchCriteria criteria) {
    /* 동적 sort 이므로 추후에 아래와 같이 적용 가능
    Sort sort = Sort.by(
            Sort.Order.asc("title"),
            Sort.Order.desc("releaseDate")
    );
    */
    Sort sort = Sort.by("releaseDate").descending();
    return movieRepositoryCustom.findByFilters(criteria, sort);
  }

  @Override
  public Optional<Movie> findById(Long movieId) {
    return movieJpaRepository.findById(movieId);
  }
}
