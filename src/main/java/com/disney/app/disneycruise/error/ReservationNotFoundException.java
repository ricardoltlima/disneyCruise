package com.disney.app.disneycruise.error;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(String id) {
        super("Reservation not found: " + id);
    }
}
