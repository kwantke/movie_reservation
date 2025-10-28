package com.example.application.port.in;

import com.example.application.dto.request.MovieRequestDto;
import com.example.application.dto.response.MovieResponseDto;

import java.util.List;

public interface MovieServicePort {
  List<MovieResponseDto> getMovies();

  void createMovie(MovieRequestDto movieRequestDto);
}
