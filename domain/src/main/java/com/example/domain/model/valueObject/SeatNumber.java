package com.example.domain.model.valueObject;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

import java.util.Set;

@Embeddable
public class SeatNumber {

  private static final Set<Character> VALID_SEAT_ROW = Set.of('A', 'B', 'C', 'D', 'E');
  private static final int MIN_SEAT_NUMBER = 1;
  private static final int MAX_SEAT_NUMBER = 5;

  private Character setRow;
  private int seatColum;
}
