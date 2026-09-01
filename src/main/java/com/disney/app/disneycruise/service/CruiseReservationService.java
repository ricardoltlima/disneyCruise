package com.disney.app.disneycruise.service;

import com.disney.app.disneycruise.dto.CruiseReservationRequest;
import com.disney.app.disneycruise.dto.CruiseReservationResponse;
import com.disney.app.disneycruise.entity.CruiseReservationEntity;
import com.disney.app.disneycruise.error.ReservationNotFoundException;
import com.disney.app.disneycruise.repository.CruiseReservationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CruiseReservationService {

    private final CruiseReservationRepository repository;

    public CruiseReservationService(CruiseReservationRepository repository) {
        this.repository = repository;
    }

    public Mono<CruiseReservationResponse> createReservation(CruiseReservationRequest request) {

        CruiseReservationEntity entity = new CruiseReservationEntity(
                null,
                request.guestId(),
                request.shipCode(),
                request.sailingDate(),
                request.stateroomType(),
                request.numberOfGuests(),
                "PENDING"
        );

        return repository.save(entity)
                .map(saved -> new CruiseReservationResponse(
                        saved.guestId(),
                        saved.shipCode(),
                        saved.sailingDate(),
                        saved.status()));
    }

    public Mono<CruiseReservationResponse> getReservation(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ReservationNotFoundException(id)))
                .map(entity -> new CruiseReservationResponse(entity.guestId(),
                        entity.shipCode(),
                        entity.sailingDate(),
                        entity.status()));
    }
}
