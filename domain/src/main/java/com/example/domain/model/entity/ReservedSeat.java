package com.example.domain.model.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class ReservedSeat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Reservation reservation;

  @ManyToOne
  @JoinColumn(nullable = false)
  private ScreeningSeat screeningSeat;


}
