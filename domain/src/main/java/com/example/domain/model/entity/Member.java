package com.example.domain.model.entity;

import com.example.domain.model.base.AuditingFields;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@Entity
@Table(name = "member")
@RequiredArgsConstructor
public class Member extends AuditingFields {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, length = 150, unique = true)
  private String email;



}
