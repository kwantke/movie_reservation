package com.example.infrastructure.rest;

import com.example.application.dto.request.MovieRequestDto;
import com.example.application.dto.request.MovieSearchCriteria;
import com.example.application.dto.request.ScreeningRequestDto;
import com.example.application.dto.response.MovieResponseDto;
import com.example.application.port.in.MovieServicePort;
import com.example.application.service.MovieSearchRateLimiterService;
import com.example.domain.exception.CustomException;
import com.example.domain.exception.ErrorCode;
import com.example.domain.validation.MovieValidation;
import jakarta.servlet.http.HttpServletRequest;
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
  private final MovieSearchRateLimiterService movieSearchRateLimiterService;

  @GetMapping
  public ResponseEntity<List<MovieResponseDto>> getMovies(
          @ModelAttribute MovieSearchCriteria criteria, // @ModelAttribute : 쿼리 파라미터를 자동으로 MovieSearchCriteria 객체 필드에 매핑
          HttpServletRequest request
  ) {

    String clientIp = request.getHeader("X-Forwarded-For"); // 프록시 서버 사용 시도에도 실제 클라이언트 IP 반환하도록 헤더 값을 읽음
    if (clientIp == null || clientIp.isEmpty()) {
      clientIp = request.getRemoteUser();
    }

    if (!movieSearchRateLimiterService.isAllowed(clientIp)) {
      throw new CustomException(ErrorCode.RATE_LIMIT_EXCEED);
    }


    movieValidation.validateTitleLength(criteria.title());

    List<MovieResponseDto> movies = movieServicePort.findMovies(criteria);
    return ResponseEntity.ok(movies);

  }

  @PostMapping
  public ResponseEntity<Void> createMovie(
          @RequestBody @Valid MovieRequestDto movieRequestDto
  ) {
    movieValidation.validateTitleLength(movieRequestDto.title());
    movieServicePort.createMovie(movieRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PostMapping("/{movieId}/screening")
  public ResponseEntity<Void> createScreening(
          @PathVariable Long movieId,
          @RequestBody @Valid ScreeningRequestDto screeningRequestDto
  ) {
    movieServicePort.addScreeningToMovie(movieId, screeningRequestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
