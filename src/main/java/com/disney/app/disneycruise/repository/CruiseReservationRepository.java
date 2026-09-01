package com.disney.app.disneycruise.repository;

import com.disney.app.disneycruise.entity.CruiseReservationEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CruiseReservationRepository extends ReactiveCrudRepository<CruiseReservationEntity, String> {
}
