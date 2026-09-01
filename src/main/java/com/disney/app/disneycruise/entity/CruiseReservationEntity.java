package com.disney.app.disneycruise.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Document("cruise_reservations")
public record CruiseReservationEntity(
        @Id String id,
        String guestId,
        String shipCode,
        OffsetDateTime sailingDate,
        String stateroomType,
        int numberOfGuests,
        String status
) {
}
