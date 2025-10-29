package com.example.application.port.out;

import com.example.domain.model.entity.Screening;

public interface ScreeningRepositoryPort {

  void save(Screening screening);
}
