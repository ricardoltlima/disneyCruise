package com.disney.app.disneycruise.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservationNotFoundException.class)
    public Mono<ResponseEntity<ApiErrorResponse>> handleReservationNotFound(
            ReservationNotFoundException ex,
            ServerWebExchange exchange
    ) {
        ApiErrorResponse body = new ApiErrorResponse(
                ex.getMessage(),
                exchange.getRequest().getPath().value(),
                OffsetDateTime.now()
        );

        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(body));
    }

}
