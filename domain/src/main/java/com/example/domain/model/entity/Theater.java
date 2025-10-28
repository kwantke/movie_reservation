package com.example.domain.model.entity;

import com.example.domain.model.base.AuditingFields;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Theater extends AuditingFields {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;


}
