package com.example.infrastructure.db.adapter;

import com.example.application.port.out.ScreeningRepositoryPort;
import com.example.domain.model.entity.Screening;
import com.example.infrastructure.db.ScreeningJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ScreeningRepositoryAdapter implements ScreeningRepositoryPort {

  private final ScreeningJpaRepository screeningJpaRepository;
  @Override
  public void save(Screening screening) {
    screeningJpaRepository.save(screening);
  }

  @Override
  public Optional<Screening> findById(Long id) {
    return screeningJpaRepository.findById(id);
  }
}
