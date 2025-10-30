package com.example.infrastructure.db.querydsl;

import com.example.domain.model.entity.Member;
import com.example.domain.model.entity.Screening;
import com.example.domain.model.projection.ReservationProjection;

import java.util.List;

public interface ReservationRepositoryCustom {

  List<ReservationProjection> findByScreeningAndMemberAndReservationSeat(Screening screening, Member member);
}
