package com.example.application.port.out;

import com.example.domain.model.entity.Member;
import com.example.domain.model.entity.Screening;
import com.example.domain.model.entity.Seat;
import com.example.domain.model.projection.ReservationProjection;

import java.util.List;

public interface ReservationRepositoryPort {

  List<ReservationProjection> findByScreeningAndMemberAndReservationSeat(Screening screening, Member member);

}
