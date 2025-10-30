package com.example.domain.validation;

import com.example.domain.exception.CustomException;
import com.example.domain.exception.ErrorCode;
import com.example.domain.model.entity.ScreeningSeat;
import com.example.domain.model.entity.Seat;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ReservationValidation {
  private static final int MAX_SEATS_PER_SCREENING = 5;

  public void validateMaxSeatsPerScreening(int existingReservations, int newSeatCount) {

  }

  public void validateSeatsExist(List<Long> requestSeatIds, List<ScreeningSeat> foundSeats) {
    if (foundSeats.size() != requestSeatIds.size()) {
      throw new CustomException(ErrorCode.INVALID_REQUEST, "Invalid seat information provided");
    }
  }

  public void validateSeatsAreConsecutive(List<Seat> seats) {
    if (seats == null || seats.isEmpty()) {
      throw new CustomException(ErrorCode.INVALID_REQUEST, "the requested Seat List are null or empty");
    }

    Map<Character, List<Integer>> rowGroupedSeats = seats.stream()
            .collect(Collectors.groupingBy(
                    seat -> seat.getSeatNumber().getSeatRow(), // 좌석의 행으로 그룹화 (A, B, C, D, E)
                    Collectors.mapping(seat -> seat.getSeatNumber().getSeatColumn(), Collectors.toList()) // 좌석의 열을 리스트로 저장
            )); // rowGroupedSeats 예시: {'A' -> [1, 2], 'B' -> [3]} (이 경우 siez()는 2개)

    if (rowGroupedSeats.size() > 1) {
      throw new CustomException(ErrorCode.SEATS_NOT_CONSECUTIVE);
    }

    List<Integer> seatNumbers = rowGroupedSeats.values().iterator().next();
    seatNumbers.sort(Integer::compareTo);

    for (int i = 0; i < seatNumbers.size() - 2; i++ ) {
      if (seatNumbers.get(i) + 1 != seatNumbers.get(i + 1)) { // 현재 좌석과 다음 좌석의 차이가 1인지 확인
        throw new CustomException(ErrorCode.SEATS_NOT_CONSECUTIVE);
      }
    }
  }
}
