package com.example.domain.model.entity;

import com.example.domain.model.base.AuditingFields;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class Reservation extends AuditingFields {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Screening screening;

  @ManyToOne
  @JoinColumn(nullable = false)
  private Member member;

  @OneToMany(mappedBy = "reservation")
  private List<ReservedSeat> reservedSeats;

}
