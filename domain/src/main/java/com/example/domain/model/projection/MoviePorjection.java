package com.example.domain.model.projection;

import java.time.LocalDate;
import java.util.List;

public interface MoviePorjection {
  Long getId();
  String getTitle();
  String getContentRating();

  LocalDate getReleaseDate();
  String getThumbnailUrl();

  int getRuntimeMinutes();
  String getGenre();
  List<ScreeningProjection> getScreenings();
}
