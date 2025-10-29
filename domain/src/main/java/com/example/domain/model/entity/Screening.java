package com.example.domain.model.entity;

import com.example.domain.model.base.AuditingFields;
import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalTime;


@Getter
@Entity
@Table(name = "screening")
public class Screening extends AuditingFields {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Movie movie;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Theater theater;

  @Column(nullable = false)
  private LocalTime startTime;

  @Column(nullable = false)
  private LocalTime endTime;

  protected Screening() {}

  private Screening(LocalTime startTime, Movie movie, Theater theater) {
    this.startTime = startTime;
    this.endTime = calculateEndTime(movie.getRuntimeMinutes());
    this.movie = movie;
    this.theater = theater;
  }


  public static Screening of(LocalTime startTime, Movie movie, Theater theater) {
    return new Screening(startTime, movie, theater);
  }

  private LocalTime calculateEndTime(int runtimeMinutes) {
    return this.startTime.plusMinutes(runtimeMinutes);
  }
}
