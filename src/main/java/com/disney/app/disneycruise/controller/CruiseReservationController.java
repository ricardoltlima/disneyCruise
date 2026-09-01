package com.disney.app.disneycruise.controller;

import com.disney.app.disneycruise.dto.CruiseReservationRequest;
import com.disney.app.disneycruise.dto.CruiseReservationResponse;
import com.disney.app.disneycruise.service.CruiseReservationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/reservations")
public class CruiseReservationController {

    private final CruiseReservationService service;

    public CruiseReservationController(CruiseReservationService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<ResponseEntity<CruiseReservationResponse>> createReservation(@Valid @RequestBody CruiseReservationRequest request) {
        URI location = URI.create("/api/v1/reservations");
        return service.createReservation(request)
                .map(response -> ResponseEntity.created(location).body(response));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CruiseReservationResponse>> getReservation(@PathVariable String id) {
        return service.getReservation(id)
                .map(ResponseEntity::ok);
    }
}
