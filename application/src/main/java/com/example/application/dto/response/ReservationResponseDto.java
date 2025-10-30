package com.example.application.dto.response;

import java.time.LocalTime;
import java.util.List;

public record ReservationResponseDto (
        String memberName,
        String movieTitle,
        String theaterName,
        LocalTime screeningStartTime,
        List<String> reservedSeats
){

}
