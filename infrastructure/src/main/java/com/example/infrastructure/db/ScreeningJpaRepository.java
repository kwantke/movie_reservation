package com.example.infrastructure.db;

import com.example.domain.model.entity.Screening;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScreeningJpaRepository extends JpaRepository<Screening, Long> {

  @EntityGraph(attributePaths = {"movie", "theater"})
  Optional<Screening> findById(Long id);
}
