package com.example.application.service;

import com.example.application.dto.response.MovieResponseDto;
import com.example.application.port.in.MovieServicePort;
import com.example.application.port.out.MovieRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MovieService implements MovieServicePort {

  private final MovieRepositoryPort movieRepositoryPort;
  @Override
  public List<MovieResponseDto> getMovies() {

    return movieRepositoryPort.findMovies().stream()
            .map(MovieResponseDto::fromEntity)
            .toList();
  }
}

