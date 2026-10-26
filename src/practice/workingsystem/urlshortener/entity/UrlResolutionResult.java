package practice.workingsystem.urlshortener.entity;

import practice.workingsystem.urlshortener.strategy.ResolutionStatus;

import java.util.Objects;

public record UrlResolutionResult(ResolutionStatus status, String originalUrl) {
    public UrlResolutionResult {
        Objects.requireNonNull(status, "status cannot be null");

        if (status == ResolutionStatus.SUCCESS) {
            Objects.requireNonNull(originalUrl, "originalUrl cannot be null for a successful resolution");
        } else if (originalUrl != null) {
            throw new IllegalArgumentException("originalUrl must be null for unsuccessful resolution");
        }
    }

    public static UrlResolutionResult success(String originalUrl) {
        return new UrlResolutionResult(ResolutionStatus.SUCCESS, originalUrl);
    }

    public static UrlResolutionResult unknown() {
        return new UrlResolutionResult(ResolutionStatus.UNKNOWN, null);
    }

    public static UrlResolutionResult expired() {
        return new UrlResolutionResult(ResolutionStatus.EXPIRED, null);
    }
}
