package com.example.infrastructure.db;

import com.example.domain.model.entity.Screening;
import com.example.domain.model.entity.ScreeningSeat;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningSeatJpaRepository extends JpaRepository<ScreeningSeat, Long> {

  @EntityGraph(attributePaths = {"seat", "seat.theater"})
  List<ScreeningSeat> findByScreeningAndSeat_IdIn(Screening screening, List<Long> seatIds);

  long countByScreeningIdAndReservedIsTrue(Long screeningId);

  long countByScreeningAndSeat_IdInAndReservedIsTrue(Screening screening, List<Long> seatIds);
}
