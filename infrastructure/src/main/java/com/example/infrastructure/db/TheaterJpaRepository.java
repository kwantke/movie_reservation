package com.example.infrastructure.db;

import com.example.domain.model.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterJpaRepository extends JpaRepository<Seat, Long> {
}
