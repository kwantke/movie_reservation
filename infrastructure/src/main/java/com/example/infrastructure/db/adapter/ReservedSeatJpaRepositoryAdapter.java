package com.example.infrastructure.db.adapter;

import com.example.application.port.out.ReservedSeatRepositoryPort;
import com.example.domain.model.entity.ReservedSeat;
import com.example.infrastructure.db.ReservedSeatJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ReservedSeatJpaRepositoryAdapter implements ReservedSeatRepositoryPort {

  private final ReservedSeatJpaRepository reservedSeatJpaRepository;
  @Override
  public void saveAll(List<ReservedSeat> reservedSeats) {
    reservedSeatJpaRepository.saveAll(reservedSeats);
  }
}
