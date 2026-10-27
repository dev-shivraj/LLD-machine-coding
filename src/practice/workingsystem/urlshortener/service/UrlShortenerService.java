package practice.workingsystem.urlshortener.service;

import practice.workingsystem.urlshortener.entity.UrlMapping;
import practice.workingsystem.urlshortener.entity.UrlResolutionResult;
import practice.workingsystem.urlshortener.repositories.UrlMappingRepository;
import practice.workingsystem.urlshortener.strategy.ShortCodeGenerator;
import practice.workingsystem.urlshortener.utils.AliasValidator;
import practice.workingsystem.urlshortener.utils.UrlValidator;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class UrlShortenerService {

    private static final int MAX_GENERATION_ATTEMPTS = 10;

    private final UrlValidator urlValidator;
    private final ShortCodeGenerator codeGenerator;
    private final UrlMappingRepository repository;
    private final Clock clock;
    private final ClickAnalyticsService clickAnalyticsService;
    private final AliasValidator aliasValidator;

    public UrlShortenerService(
            UrlValidator urlValidator,
            AliasValidator aliasValidator,
            ShortCodeGenerator codeGenerator,
            UrlMappingRepository repository,
            Clock clock, ClickAnalyticsService clickAnalyticsService
    ) {
        this.urlValidator = Objects.requireNonNull(urlValidator, "urlValidator cannot be null");
        this.aliasValidator = Objects.requireNonNull(aliasValidator, "aliasValidator cannot be null");
        this.codeGenerator = Objects.requireNonNull(codeGenerator, "codeGenerator cannot be null");
        this.repository = Objects.requireNonNull(repository, "repository cannot be null");
        this.clock = Objects.requireNonNull(clock, "clock cannot be null");
        this.clickAnalyticsService = Objects.requireNonNull(clickAnalyticsService, "clickAnalyticsService cannot be null");
    }

    public String shortenUrl(String originalUrl) {
        return shortenUrl(originalUrl, (Duration) null);
    }

    public String shortenUrl(String originalUrl, Duration ttl) {

        if (!urlValidator.isValid(originalUrl)) {
            throw new IllegalArgumentException("Invalid URL: " + originalUrl);
        }

        validateTtl(ttl);

        for (int attempt = 0; attempt < MAX_GENERATION_ATTEMPTS; attempt++) {
            String shortCode = codeGenerator.generate();
            if (shortCode == null || shortCode.isBlank()) {
                throw new IllegalStateException("Code generator returned an invalid code");
            }

            if (repository.existByShortCode(shortCode)) {
                continue;
            }

            Instant createdAt = clock.instant();
            Instant expiresAt = ttl == null ? null : createdAt.plus(ttl);
            UrlMapping mapping = new UrlMapping(shortCode, originalUrl, createdAt, expiresAt);

            repository.save(mapping);
            return shortCode;
        }

        throw new IllegalStateException("Unable to generate a unique short code");
    }

    public String shortenUrl(String originalUrl, String customAlias) {

        return shortenUrl(originalUrl, customAlias, null);

    }

    public String shortenUrl(String originalUrl, String customAlias, Duration ttl) {

        if (!urlValidator.isValid(originalUrl)) {
            throw new IllegalArgumentException("Invalid URL: " + originalUrl);
        }

        aliasValidator.validate(customAlias);
        validateTtl(ttl);

        UrlMapping existingMapping = repository
                .findByShortCode(customAlias)
                .orElse(null);

        if (existingMapping != null) {
            if (!existingMapping.isExpired(clock.instant())) {
                throw new IllegalArgumentException("Alias is already in use: " + customAlias);
            }

            repository.deleteByShortCode(customAlias);
            clickAnalyticsService.deleteAnalytics(customAlias);
        }

        Instant createdAt = clock.instant();
        Instant expiresAt = ttl == null
                ? null
                : createdAt.plus(ttl);

        UrlMapping mapping = new UrlMapping(
                customAlias,
                originalUrl,
                createdAt,
                expiresAt
        );

        repository.save(mapping);
        return customAlias;

    }

    public UrlResolutionResult resolveShortCode(String shortCode) {
        if (shortCode == null || shortCode.isBlank()) {
            return UrlResolutionResult.unknown();
        }

        UrlMapping mapping = repository.findByShortCode(shortCode)
                .orElse(null);

        if (mapping == null) {
            return UrlResolutionResult.unknown();
        }

        if (mapping.isExpired(clock.instant())) {
            return UrlResolutionResult.expired();
        }

        clickAnalyticsService.recordClick(shortCode);

        return UrlResolutionResult.success(mapping.getOriginalUrl());
    }

    private void validateTtl(Duration ttl) {
        if (ttl != null && (ttl.isZero() || ttl.isNegative())) {
            throw new IllegalArgumentException("TTL must be greater than zero");
        }
    }
}