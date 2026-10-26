package practice.workingsystem.urlshortener.entity;

import java.time.Instant;
import java.util.Objects;

public class UrlMapping {
    private final String shortCode;
    private final String originalUrl;
    private final Instant createdAt;
    private final Instant expiresAt;

    public UrlMapping(String shortCode, String originalUrl, Instant createdAt, Instant expiresAt) {
        this.shortCode = Objects.requireNonNull(shortCode);
        this.originalUrl = Objects.requireNonNull(originalUrl);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.expiresAt = expiresAt;
    }


    public String getShortCode() {
        return shortCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public boolean isExpired(Instant currentTime) {
        Objects.requireNonNull(currentTime);

        return expiresAt != null && !currentTime.isBefore(expiresAt);
    }
}
