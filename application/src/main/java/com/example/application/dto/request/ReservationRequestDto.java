package com.example.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ReservationRequestDto(
        @NotNull(message = "Screening Id must not be null")
        Long screeningId,
        @NotNull(message = "Member Id must not be null")
        Long memberId,
        @NotNull(message = "Seat Id must not be null")
        @Size(min = 1, message = "At least one seat Id must be provided")
        List<Long> seatIds
) {
}
