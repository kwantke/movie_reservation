package com.example.domain.model.entity;

import com.example.domain.exception.CustomException;
import com.example.domain.exception.ErrorCode;
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

  private boolean reserved;
  @Version
  private int version;

  public void reserve() {
    if (reserved) {
      throw new CustomException(ErrorCode.SEAT_ALREADY_RESERVED);
    }
    this.reserved = true;
  }
}
