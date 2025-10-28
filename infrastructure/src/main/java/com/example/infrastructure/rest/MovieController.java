package com.example.infrastructure.rest;

import com.example.application.dto.request.MovieRequestDto;
import com.example.application.dto.request.MovieSearchCriteria;
import com.example.application.dto.response.MovieResponseDto;
import com.example.application.port.in.MovieServicePort;
import com.example.domain.model.validation.MovieValidation;
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
  private final MovieValidation movieValidation;


  @GetMapping
  public ResponseEntity<List<MovieResponseDto>> getMovies(
          @ModelAttribute MovieSearchCriteria criteria // @ModelAttribute : 쿼리 파라미터를 자동으로 MovieSearchCriteria 객체 필드에 매핑
  ) {

    movieValidation.validateTitleLength(criteria.title());

    List<MovieResponseDto> movies = movieServicePort.findMovies(criteria);
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
