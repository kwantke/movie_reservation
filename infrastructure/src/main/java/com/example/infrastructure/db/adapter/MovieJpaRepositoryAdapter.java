package com.example.infrastructure.db.adapter;

import com.example.application.port.out.MovieRepositoryPort;
import com.example.domain.model.entity.Movie;
import com.example.infrastructure.db.MovieJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MovieJpaRepositoryAdapter implements MovieRepositoryPort {

  private final MovieJpaRepository movieJpaRepository;
  @Override
  public List<Movie> findMovies() {
    return movieJpaRepository.findAll();
  }
}
