package com.example.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {


  // AuditorAware : 현재 작업 사용자 정보 제공
  // @CreatedBy, @LastModifiedBy 값이 자동으로 채워짐
  @Bean
  public AuditorAware<String> auditorProvider() {
    // todo: 현재 사용자는 임의로 system 고정, 추후 실제 사용자 정보를 불러 오는 로직 작성
    return () -> Optional.of("System");
  }

}
