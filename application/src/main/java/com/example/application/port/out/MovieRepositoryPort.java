package com.example.application.port.out;

import com.example.application.dto.request.MovieSearchCriteria;
import com.example.domain.model.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepositoryPort {
  List<Movie> findMovies();

  void save(Movie movie);

  List<Movie> findBy(MovieSearchCriteria criteria);

  Optional<Movie> findById(Long movieId);
}
