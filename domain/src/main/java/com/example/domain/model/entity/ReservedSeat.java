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

  protected ReservedSeat() {}
  public ReservedSeat(Reservation reservation, ScreeningSeat screeningSeat) {
    this.reservation = reservation;
    this.screeningSeat = screeningSeat;
  }

  public static ReservedSeat of(Reservation reservation, ScreeningSeat screeningSeat) {
    return new ReservedSeat(reservation, screeningSeat);
  }
}
