package com.example.application.port.out;

import com.example.domain.model.entity.Movie;

import java.util.List;

public interface MovieRepositoryPort {
  List<Movie> findMovies();
}
