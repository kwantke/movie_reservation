package com.example.infrastructure.db.adapter;

import com.example.application.port.out.TheaterRepositoryPort;
import com.example.domain.model.entity.Theater;
import com.example.infrastructure.db.TheaterJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TheaterJpaRepositoryAdapter implements TheaterRepositoryPort {

  private final TheaterJpaRepository theaterJpaRepository;
  @Override
  public Optional<Theater> findById(Long id) {
    return theaterJpaRepository.findById(id);
  }
}
