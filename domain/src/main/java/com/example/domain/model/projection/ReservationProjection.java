package com.example.domain.model.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReservationProjection {

  private Long screeningId;
  private Long memberId;
  private LocalTime startTime;
  private LocalTime endTime;
  private int seatColum;
  private Character seatRow;

}
