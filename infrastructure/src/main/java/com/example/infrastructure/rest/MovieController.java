package com.example.infrastructure.rest;

import com.example.application.dto.request.MovieRequestDto;
import com.example.application.dto.response.MovieResponseDto;
import com.example.application.port.in.MovieServicePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @PostMapping
  public ResponseEntity<List<MovieResponseDto>> createMovie(
          @RequestBody @Valid MovieRequestDto movieRequestDto
  ) {
    movieServicePort.createMovie(movieRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
