package com.disney.app.disneycruise.logging;

import org.jspecify.annotations.NonNull;

public record UserRecord(
        String username,

        @Sensitive String ssn,
        @Sensitive String creditCard,
        @Sensitive String password,

        String email
) {
    @Override
    public @NonNull String toString() {
        return RecordMasker.toString(this);
    }
}

