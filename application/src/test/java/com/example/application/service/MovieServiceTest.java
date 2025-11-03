package com.example.application.service;

import com.example.application.dto.request.MovieRequestDto;
import com.example.application.dto.request.MovieSearchCriteria;
import com.example.application.dto.request.ScreeningRequestDto;
import com.example.application.dto.response.MovieResponseDto;
import com.example.application.port.out.MovieRepositoryPort;
import com.example.application.port.out.ScreeningRepositoryPort;
import com.example.application.port.out.TheaterRepositoryPort;
import com.example.domain.exception.CustomException;
import com.example.domain.exception.ErrorCode;
import com.example.domain.model.entity.Movie;
import com.example.domain.model.entity.Theater;
import com.example.domain.model.valueObject.ContentRating;
import com.example.domain.model.valueObject.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Mockito 기능(@Mock, @InjectMocks 등)을 자동으로 활성화
class MovieServiceTest {

  @Mock private MovieRepositoryPort movieRepositoryPort;

  @Mock private ScreeningRepositoryPort screeningRepositoryPort;

  @Mock private TheaterRepositoryPort theaterRepositoryPort;

  @InjectMocks // Mockito가 MovieService 객체를 만들고, 그 안에 필요한 의존성(Mock 객체들)을 자동으로 주입해 줌
  private MovieService sut; // “System Under Test”의 약자로 테스트의 대상 객체를 말함

  private Movie mockMovie;
  private Theater mockTheater;
  private MovieRequestDto movieRequestDto;
  private ScreeningRequestDto screeningRequestDto;


  @BeforeEach
  void setUp() {
    mockMovie = Movie.of(
            "Test Movie",
            ContentRating.ADULT,
            LocalDate.of(2023, 12, 1),
            "https://test-thumbnail.com",
            120,
            Genre.ACTION
    );

    mockTheater = mock(Theater.class);

    movieRequestDto = new MovieRequestDto(
            "Test Movie",
            ContentRating.ADULT,
            LocalDate.of(2023, 12, 1),
            "https://test-thumbnail.com",
            120,
            Genre.ACTION
    );

    screeningRequestDto = new ScreeningRequestDto(
            1L,
            LocalTime.of(10, 30)
    );
  }

  @DisplayName("제목과 장르로 영화를 검색하면 일치하는 목록을 반환한다.")
  @Test
  void givenTitleAndGenre_whenFindMoives_thenReturnMovieList() {
    //Given
    MovieSearchCriteria criteria = new MovieSearchCriteria("Test", Genre.ACTION);

    when(movieRepositoryPort.findBy(criteria)).thenReturn(List.of(mockMovie));

    //When
    List<MovieResponseDto> result = sut.findMovies(criteria);

    //Then
    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
    assertEquals("Test Movie", result.get(0).title());
    verify(movieRepositoryPort, times(1)).findBy(criteria); //메서드가 정확히 한 번 호출되었는지 확인

  }

  @DisplayName("검색 결과가 없을 경우 빈 리스트를 반환한다")
  @Test
  void givenNoMatchingMovies_whenFindMovies_thenReturnEmptyList() {
    //Given
    MovieSearchCriteria criteria = new MovieSearchCriteria("Nonexist", Genre.COMEDY);

    when(movieRepositoryPort.findBy(criteria)).thenReturn(Collections.emptyList());

    //When
    List<MovieResponseDto> result = sut.findMovies(criteria);

    //Then
    assertTrue(result.isEmpty());
    verify(movieRepositoryPort, times(1)).findBy(criteria);
  }

  @DisplayName("영화 생성 요청이 들어오면 영화가 저장된다")
  @Test
  void givenValidRequest_whenCreateMovie_thenSaveMovie() {
    //When
    sut.createMovie(movieRequestDto);

    //Then
    verify(movieRepositoryPort, times(1)).save(any(Movie.class));
  }

  @DisplayName("존재하지 않는 영화 ID로 상영 일정을 추가가면 예외가 발생한다.")
  @Test
  void givenInvalidMovieId_whenAddScreeing_thenThrowException() {
    //Given
    when(movieRepositoryPort.findById(1L)).thenReturn(Optional.empty());

    //When
    CustomException exception = assertThrows(CustomException.class,
            () -> sut.addScreeningToMovie(1L, screeningRequestDto));

    //Then
    assertEquals(ErrorCode.INVALID_MOVIE, exception.getErrorCode());
    verify(movieRepositoryPort, times(1)).findById(1L);
    verify(screeningRepositoryPort, never()).save(any());

  }

  @DisplayName("존재하지 않는 상영관 ID로 상영 일정을 추가하면 예외가 발생한다")
  @Test
  void givenInvalidTheaterId_whenAddScreening_thenThrowException() {
    //Given
    when(movieRepositoryPort.findById(1L)).thenReturn(Optional.of(mockMovie));
    when(theaterRepositoryPort.findById(1L)).thenReturn(Optional.empty());

    //When
    CustomException exception = assertThrows(CustomException.class,
            () -> sut.addScreeningToMovie(1L, screeningRequestDto));
    //Then
    assertEquals(ErrorCode.INVALID_THEATER, exception.getErrorCode());
    verify(movieRepositoryPort, times(1)).findById(1L);
    verify(theaterRepositoryPort, times(1)).findById(1L);
    verify(screeningRepositoryPort, never()).save(any()); // 상영 일정 저장 로직이 실행되지 않음
  }

}