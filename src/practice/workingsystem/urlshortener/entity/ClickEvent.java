package practice.workingsystem.urlshortener.entity;

import java.time.Instant;
import java.util.Objects;

public record ClickEvent(String shortCode, Instant timestamp) {
    public ClickEvent {
        Objects.requireNonNull(shortCode, "shortCode cannot be null");
        Objects.requireNonNull(timestamp, "timestamp cannot be null");

        if (shortCode.isBlank()) {
            throw new IllegalArgumentException("shortCode cannot be blank");
        }
    }
}