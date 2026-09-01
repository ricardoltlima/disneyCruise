package com.disney.app.disneycruise.dto;

import java.time.OffsetDateTime;

public record CruiseReservationResponse(

        String guestId,
        String shipCode,
        OffsetDateTime sailingDate,
        String reservationStatus
) {
}
