package com.disney.app.disneycruise.logging;

import java.lang.reflect.Field;
import java.util.StringJoiner;

class RecordMasker {
    public static String toString(Record record) {
        Class<?> clazz = record.getClass();
        StringJoiner joiner = new StringJoiner(", ", clazz.getSimpleName() + "[", "]");

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isSynthetic()) continue;

            field.setAccessible(true);
            try {
                Object value = field.get(record);
                if (field.isAnnotationPresent(Sensitive.class) && value != null) {
                    String rawStr = value.toString();
                    int length = rawStr.length();

                    if (length <= 2) {
                        // If it's too short, mask everything to stay safe
                        value = "*".repeat(length);
                    } else {
                        // Mask all except the last 2 digits
                        String maskedPart = "*".repeat(length - 2);
                        String visiblePart = rawStr.substring(length - 2);
                        value = maskedPart + visiblePart;
                    }
                }
                joiner.add(field.getName() + "=" + value);
            } catch (IllegalAccessException e) {
                joiner.add(field.getName() + "=[PROTECTION_ERROR]");
            }
        }
        return joiner.toString();
    }
}


