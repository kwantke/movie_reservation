package com.example.application.service;

import com.example.application.dto.request.ReservationRequestDto;
import com.example.application.dto.response.ReservationResponseDto;
import com.example.application.port.in.ReservationServicePort;
import com.example.application.port.out.*;
import com.example.domain.exception.CustomException;
import com.example.domain.exception.ErrorCode;
import com.example.domain.model.entity.*;
import com.example.domain.validation.ReservationValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService implements ReservationServicePort {

  private final ScreeningRepositoryPort screeningRepositoryPort;
  private final MemberRepositoryPort memberRepositoryPort;
  private final ScreeningSeatRepositoryPort screeningSeatRepositoryPort;
  private final ReservationRepositoryPort reservationRepositoryPort;
  private final ReservedSeatRepositoryPort reservedSeatRepositoryPort;
  private final ReservationValidation reservationValidation;


  @Override
  public ReservationResponseDto create(ReservationRequestDto request) {
    Screening screening = getScreening(request.screeningId());
    Member member = getMember(request.memberId());

    List<ScreeningSeat> requestedSeats = validateReservationConstraints(screening, member, request.seatIds());

    Reservation reservation = saveReservationAndSeats(screening, member, requestedSeats);



    return null;
  }

  /** 예약 및 좌석 저장 */
  private Reservation saveReservationAndSeats(Screening screening, Member member, List<ScreeningSeat> requestedSeats) {
    Reservation reservation = Reservation.of(screening, member);
    reservationRepositoryPort.save(reservation);

    List<ReservedSeat> reservedSeats = new ArrayList<>();

    for (ScreeningSeat screeningSeat : requestedSeats) {
      screeningSeat.reserve(); // 좌석을 예약 상태로 변경
      screeningSeatRepositoryPort.saveAndFlush(screeningSeat);
      ReservedSeat reservedSeat = ReservedSeat.of(reservation, screeningSeat);
      reservedSeats.add(reservedSeat);
    }

    reservedSeatRepositoryPort.saveAll(reservedSeats); // 예약한 좌석 리스트 한 번에 추가
    reservation.addReservedSeats(reservedSeats); // 예약 정보에 예약한 좌석 리스트 추가

    return reservation;
  }

  /** 예약 전 비즈니스 로직 기반으로 요청 값 검증 */
  private List<ScreeningSeat> validateReservationConstraints(Screening screening, Member member, List<Long> seatIds) {

    // 요청 죄석이 존재하는지 조회
    List<ScreeningSeat> requestedSeats = screeningSeatRepositoryPort.findByScreeningAndSeatIds(screening, seatIds);

    // 요청된 좌석 ID 목록과 조회된 좌석 리스트를 검증 레이어에서 검증
    reservationValidation.validateSeatsExist(seatIds, requestedSeats);

    // 해당 회원이 이미 예약한 좌석 수 확인
    int existingReservations = reservationRepositoryPort
                                          .findByScreeningAndMemberAndReservationSeat(screening, member)
                                          .size();
    reservationValidation.validateMaxSeatsPerScreening(existingReservations, requestedSeats.size());

    // 좌석이 연속된 형태인지 검증
    reservationValidation.validateSeatsAreConsecutive(requestedSeats.stream().map(ScreeningSeat::getSeat).toList());


    return requestedSeats;
  }

  /** 회원 정보 조회 */
  private Member getMember(Long memberId) {
    return memberRepositoryPort.findById(memberId)
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_MEMBER));
  }

  /** 상영 정보 조회 */
  private Screening getScreening(Long screeningId) {
    return screeningRepositoryPort.findById(screeningId)
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_SCREENING));
  }
}
