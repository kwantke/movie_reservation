package com.example.infrastructure.db.jdbc;

import com.example.domain.model.entity.ReservedSeat;

import java.util.List;

public interface ReservedSeatJdbcRepository {
  void saveAll(List<ReservedSeat> reservedSeats);

}
