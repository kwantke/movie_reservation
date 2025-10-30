package com.example.domain.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class ScreeningSeat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Screening screening;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Seat seat;
}
