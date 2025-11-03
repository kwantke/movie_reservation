package com.example.infrastructure.rest;

import com.example.application.dto.request.ReservationRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("[통합 테스트] 예약 컨트롤러")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class ReservationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;


  @DisplayName("정상적인 예약 요청 시 200 응답을 반환한다.")
  @Test
  void givenValidRequest_whenCreateReservation_thenReturn200() throws Exception {
    //Given
    ReservationRequestDto reservationRequestDto = new ReservationRequestDto(1L, 1L, List.of(1L, 2L, 3L));

    //When & Then
    mockMvc.perform(post("/api/v1/reservations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(reservationRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.memberName").exists())
            .andExpect(jsonPath("$.movieTitle").exists())
            .andExpect(jsonPath("$.theaterName").exists())
            .andExpect(jsonPath("$.screeningStartTime").exists())
            .andExpect(jsonPath("$.reservedSeats").exists())
            .andExpect(jsonPath("$.reservedSeats").isArray());
  }

  @DisplayName("잘못된 요청 값으로 시도하면 400 응답을 반환한다.")
  @Test
  public void givenInvalidRequest_whenCreateReservation_thenReturn400() throws Exception {
    //Given
    ReservationRequestDto reservationRequestDto = new ReservationRequestDto(1L, 3L, List.of());

    //When & Then
    mockMvc.perform(post("/api/v1/reservations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(reservationRequestDto)))
            .andExpect(status().isBadRequest());
  }
}