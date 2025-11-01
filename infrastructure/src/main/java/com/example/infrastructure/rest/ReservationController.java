package com.example.infrastructure.rest;

import com.example.application.dto.request.ReservationRequestDto;
import com.example.application.dto.response.ReservationResponseDto;
import com.example.application.port.in.ReservationServicePort;
import com.example.application.service.ReservationRateLimiterService;
import com.example.domain.exception.CustomException;
import com.example.domain.exception.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

  private final ReservationServicePort reservationServicePort;
  private final ReservationRateLimiterService reservationRateLimiterService;

  @PostMapping
  public ResponseEntity<ReservationResponseDto> createReservation(@RequestBody @Valid ReservationRequestDto request) {

    if (!reservationRateLimiterService.isAllowed(request.screeningId(), request.memberId())) {
      throw new CustomException(ErrorCode.TOO_MANY_RESERVATIONS);
    }

    ReservationResponseDto response = reservationServicePort.create(request);

    return ResponseEntity.ok(response);
  }
}
