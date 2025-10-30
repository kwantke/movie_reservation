package com.example.infrastructure.db.querydsl;

import com.example.domain.model.entity.*;
import com.example.domain.model.projection.ReservationProjection;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationRepositoryCustomImpl extends QuerydslRepositorySupport implements ReservationRepositoryCustom{

  public ReservationRepositoryCustomImpl() {
    super(Reservation.class);
  }

  @Override
  public List<ReservationProjection> findByScreeningAndMemberAndReservationSeat(Screening reqScreening, Member reqMember) {

    QReservation reservation = QReservation.reservation;
    QScreening screening = QScreening.screening;
    QMember member = QMember.member;
    QReservedSeat reservationSeat = QReservedSeat.reservedSeat;
    QScreeningSeat screeningSeat = QScreeningSeat.screeningSeat;
    QSeat seat = QSeat.seat;

    BooleanBuilder builder = new BooleanBuilder();

    Long screeningId = reqScreening.getId();
    Long memberId = reqMember.getId();

    if(null != screeningId){
      builder.and(screeningSeat.screening.id.eq(screeningId));
    }
    if (null != memberId) {
      builder.and(reservation.member.id.eq(memberId));
    }

    JPQLQuery<ReservationProjection> result =
    from(reservation)
            .innerJoin(reservation.screening, screening)
            .innerJoin(reservation.reservedSeats, reservationSeat)
            .innerJoin(reservationSeat.screeningSeat,screeningSeat)
            .innerJoin(screeningSeat.seat, seat)
            .select(Projections.constructor(
                    ReservationProjection.class,
                    reservation.screening.id,
                    reservation.member.id,
                    screening.startTime,
                    screening.endTime,
                    seat.seatNumber.seatColumn,
                    seat.seatNumber.seatRow
            ))
            .where(builder);


    return result.fetch();
  }
}
