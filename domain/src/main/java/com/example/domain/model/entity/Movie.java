package com.example.domain.model.entity;

import com.example.domain.model.base.AuditingFields;
import com.example.domain.model.converter.ContentRatingConverter;
import com.example.domain.model.valueObject.ContentRating;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Movie extends AuditingFields {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ContentRating contentRating;

  @Column(nullable = false)
  private LocalDate releaseDate;

  @Setter
  private String thumbnailUrl;

  @Setter
  private int runtimeMinutes;

  @Setter
  @Column(nullable = false)
  private String genre;

  @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Screening> screenings = new ArrayList<>();

}
