package com.example.infrastructure.db.adapter;

import com.example.application.port.out.ReservationRepositoryPort;
import com.example.domain.model.entity.Member;
import com.example.domain.model.entity.Screening;
import com.example.domain.model.entity.Seat;
import com.example.domain.model.projection.ReservationProjection;
import com.example.infrastructure.db.ReservationJpaRepository;
import com.example.infrastructure.db.querydsl.ReservationRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ReservationJpaRepositoryAdapter implements ReservationRepositoryPort {

  private final ReservationRepositoryCustom reservationRepositoryCustom;

  @Override
  public List<ReservationProjection> findByScreeningAndMemberAndReservationSeat(Screening screening, Member member) {
    return reservationRepositoryCustom.findByScreeningAndMemberAndReservationSeat(screening, member);
  }

}
