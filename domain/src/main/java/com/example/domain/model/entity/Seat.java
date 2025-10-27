package com.example.domain.model.entity;

import com.example.domain.model.base.AuditingFields;
import com.example.domain.model.valueObject.SeatNumber;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Seat extends AuditingFields {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private SeatNumber seatNumber;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Theater theater;


}
