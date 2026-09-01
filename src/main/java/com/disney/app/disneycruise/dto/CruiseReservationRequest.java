package com.disney.app.disneycruise.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record CruiseReservationRequest(

        @NotBlank
        String guestId,

        @NotBlank
        String shipCode,

        @NotNull
        @Future(message = "Sailing Date must be in the future")
        OffsetDateTime sailingDate,

        @NotNull
        String stateroomType,

        @Min(1)
        int numberOfGuests
) {
}
