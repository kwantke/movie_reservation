package com.example.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@EntityScan(basePackages = "com.example.domain.model.entity") // 엔티티 스캔
@ComponentScan(basePackages =  {"com.example"}) // 모든 모듈의 빈 스캔
@SpringBootApplication
public class InfrastructureApplication {

  public static void main(String[] args) {
    SpringApplication.run(InfrastructureApplication.class, args);
  }

}
