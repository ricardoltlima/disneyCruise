package com.disney.app.disneycruise.service;

import com.disney.app.disneycruise.dto.CruiseReservationRequest;
import com.disney.app.disneycruise.dto.CruiseReservationResponse;
import com.disney.app.disneycruise.entity.CruiseReservationEntity;
import com.disney.app.disneycruise.repository.CruiseReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CruiseReservationServiceTest {

    @Mock
    private CruiseReservationRepository repository;

    @InjectMocks
    private CruiseReservationService service;

    @Test
    void createReservationShouldSaveReservationAndReturnResponse() {

        // arrange
        CruiseReservationEntity savedEntity = new CruiseReservationEntity("res-123", "guest-123", "ship-123", OffsetDateTime.now().plusDays(30), "type-123", 2, "PENDING");

        // act
        when(repository.save(any(CruiseReservationEntity.class)))
                .thenReturn(Mono.just(savedEntity));

        Mono<CruiseReservationResponse> result = service.createReservation(new CruiseReservationRequest("guest-123", "ship-123", OffsetDateTime.now().plusDays(30), "type-123", 2));

        // assert with StepVerifier
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.guestId()).isEqualTo("guest-123");
                    assertThat(response.reservationStatus()).isEqualTo("PENDING");
                })
                .verifyComplete();
    }

    @Test
    void createReservationShouldSavePendingReservationEntity() {
        // arrange
        OffsetDateTime sailingDate = OffsetDateTime.now().plusDays(30);

        CruiseReservationRequest request = new CruiseReservationRequest(
                "guest-123",
                "ship-123",
                sailingDate,
                "BALCONY",
                2
        );

        CruiseReservationEntity savedEntity = new CruiseReservationEntity(
                "res-123",
                "guest-123",
                "ship-123",
                sailingDate,
                "BALCONY",
                2,
                "PENDING"
        );

        when(repository.save(any(CruiseReservationEntity.class)))
                .thenReturn(Mono.just(savedEntity));

        // act
        StepVerifier.create(service.createReservation(request))
                .expectNextCount(1)
                .verifyComplete();

        // assert
        ArgumentCaptor<CruiseReservationEntity> captor =
                ArgumentCaptor.forClass(CruiseReservationEntity.class);

        verify(repository).save(captor.capture());

        CruiseReservationEntity entityToSave = captor.getValue();

        assertThat(entityToSave.id()).isNull();
        assertThat(entityToSave.guestId()).isEqualTo("guest-123");
        assertThat(entityToSave.shipCode()).isEqualTo("ship-123");
        assertThat(entityToSave.sailingDate()).isEqualTo(sailingDate);
        assertThat(entityToSave.stateroomType()).isEqualTo("BALCONY");
        assertThat(entityToSave.numberOfGuests()).isEqualTo(2);
        assertThat(entityToSave.status()).isEqualTo("PENDING");
    }

    @Test
    public void getReservationByIdShouldReturnResponse() {

        OffsetDateTime sailingDate = OffsetDateTime.now().plusDays(30);

        CruiseReservationRequest request = new CruiseReservationRequest(
                "guest-123",
                "ship-123",
                sailingDate,
                "BALCONY",
                2
        );

        CruiseReservationEntity savedEntity = new CruiseReservationEntity(
                "res-123",
                "guest-123",
                "ship-123",
                sailingDate,
                "BALCONY",
                2,
                "PENDING"
        );

        when(repository.findById("res-123")).thenReturn(Mono.just(savedEntity));

        Mono<CruiseReservationResponse> reservation = service.getReservation("res-123");

        StepVerifier.create(reservation)
                .assertNext(response -> {
                    assertThat(response.guestId()).isEqualTo("guest-123");
                    assertThat(response.shipCode()).isEqualTo("ship-123");
                    assertThat(response.sailingDate()).isEqualTo(sailingDate);
                    assertThat(response.reservationStatus()).isEqualTo("PENDING");
                })
                .verifyComplete();
    }
}