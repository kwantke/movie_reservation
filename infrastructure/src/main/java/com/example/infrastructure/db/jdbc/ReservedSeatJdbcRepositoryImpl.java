package com.example.infrastructure.db.jdbc;

import com.example.domain.model.entity.ReservedSeat;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservedSeatJdbcRepositoryImpl implements ReservedSeatJdbcRepository {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public void saveAll(List<ReservedSeat> commands) { // Bulk Insert 구현
    String RESERVED_SEAT_SQL = "insert into reserved_seat (reservation_id,screening_seat_id)  " +
            "VALUES (?, ?)";
    jdbcTemplate.batchUpdate(RESERVED_SEAT_SQL, new BatchPreparedStatementSetter() {
              @Override
              public void setValues(PreparedStatement ps, int i) throws SQLException {
                ReservedSeat command = commands.get(i);
                ps.setLong(1, command.getReservation().getId());
                ps.setLong(2, command.getScreeningSeat().getId());

              }


      @Override
              public int getBatchSize() {
                return commands.size();
              }
            }
    );
  }
}
