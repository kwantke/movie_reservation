package com.example.domain.model.entity;

import com.example.domain.model.base.AuditingFields;
import com.example.domain.model.converter.ContentRatingConverter;
import com.example.domain.model.converter.GenreConverter;
import com.example.domain.model.valueObject.ContentRating;
import com.example.domain.model.valueObject.Genre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Movie extends AuditingFields {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Convert(converter = ContentRatingConverter.class)
  @Column(nullable = false)
  private ContentRating contentRating;

  @Column(nullable = false)
  private LocalDate releaseDate;

  @Setter
  private String thumbnailUrl;

  @Setter
  private int runtimeMinutes;

  @Convert(converter = GenreConverter.class)
  @Column(nullable = false)
  private Genre genre;

  @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Screening> screenings = new ArrayList<>();


  private Movie(String title,
                ContentRating contentRating,
                LocalDate releaseDate,
                String thumbnailUrl,
                int runtimeMinutes,
                Genre genre) {
    this.title = title;
    this.contentRating = contentRating;
    this.releaseDate = releaseDate;
    this.thumbnailUrl = thumbnailUrl;
    this.runtimeMinutes = runtimeMinutes;
    this.genre = genre;
  }

  public static Movie of(String title,
                         ContentRating contentRating,
                         LocalDate releaseDate,
                         String thumbnailUrl,
                         int runtimeMinutes,
                         Genre genre) {

    return new Movie(title, contentRating, releaseDate, thumbnailUrl, runtimeMinutes, genre);
  }
}
