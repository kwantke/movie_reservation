package com.example.infrastructure.db.adapter;

import com.example.application.port.out.ReservedSeatRepositoryPort;
import com.example.domain.model.entity.ReservedSeat;
import com.example.infrastructure.db.jdbc.ReservedSeatJdbcRepository;
import com.example.infrastructure.db.ReservedSeatJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Repository
public class ReservedSeatJpaRepositoryAdapter implements ReservedSeatRepositoryPort {

  private final ReservedSeatJpaRepository reservedSeatJpaRepository;
  private final ReservedSeatJdbcRepository reservedSeatJdbcRepository;
  @Override
  public void saveAll(List<ReservedSeat> reservedSeats) {

    // 기본 Insert 수행
    //reservedSeatJpaRepository.saveAll(reservedSeats);

    // Bulk Insert 수행
    reservedSeatJdbcRepository.saveAll(reservedSeats);
  }
}
