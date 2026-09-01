package com.disney.app.disneycruise.error;

import java.time.OffsetDateTime;

public record ApiErrorResponse(
        String message,
        String path,
        OffsetDateTime timestamp
) {
}
