package com.example.infrastructure.rest;

import com.example.application.dto.response.MovieResponseDto;
import com.example.application.port.in.MovieServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

  private final MovieServicePort movieServicePort;


  @GetMapping
  public ResponseEntity<List<MovieResponseDto>> getMovies() {

    List<MovieResponseDto> movies = movieServicePort.getMovies();
    return ResponseEntity.ok(movies);

  }
}
