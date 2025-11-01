package com.example.infrastructure.db.adapter;

import com.example.application.port.out.ScreeningSeatRepositoryPort;
import com.example.domain.model.entity.Screening;
import com.example.domain.model.entity.ScreeningSeat;
import com.example.infrastructure.db.ScreeningSeatJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ScreeningSeatJpaRepositoryAdapter implements ScreeningSeatRepositoryPort {

  private final ScreeningSeatJpaRepository screeningSeatJpaRepository;
  @Override
  public List<ScreeningSeat> findByScreeningAndSeatIds(Screening screening, List<Long> seatIds) {
    return screeningSeatJpaRepository.findByScreeningAndSeat_IdIn(screening, seatIds);
  }

  @Override
  public void saveAndFlush(ScreeningSeat screeningSeat) {
    screeningSeatJpaRepository.saveAndFlush(screeningSeat);
  }

  @Override
  public long countReservedSeats(Long screeningId) {
    return screeningSeatJpaRepository.countByScreeningIdAndReservedIsTrue(screeningId);
  }

  @Override
  public long countByScreeningAndSeat_IdInAndReservedIsTrue(Screening screening, List<Long> seatIds) {
    return screeningSeatJpaRepository.countByScreeningAndSeat_IdInAndReservedIsTrue(screening, seatIds);
  }
}
