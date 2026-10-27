package practice.workingsystem.urlshortener.service;

import practice.workingsystem.urlshortener.entity.UrlMapping;
import practice.workingsystem.urlshortener.entity.UrlResolutionResult;
import practice.workingsystem.urlshortener.repositories.UrlMappingRepository;
import practice.workingsystem.urlshortener.strategy.ShortCodeGenerator;

import java.time.Clock;
import java.time.Instant;
import java.util.Objects;

public class UrlShortenerService {

    private static final int MAX_GENERATION_ATTEMPTS = 10;

    private final UrlValidator urlValidator;
    private final ShortCodeGenerator codeGenerator;
    private final UrlMappingRepository repository;
    private final Clock clock;
    private final ClickAnalyticsService clickAnalyticsService;

    public UrlShortenerService(
            UrlValidator urlValidator,
            ShortCodeGenerator codeGenerator,
            UrlMappingRepository repository,
            Clock clock, ClickAnalyticsService clickAnalyticsService
    ) {
        this.urlValidator = Objects.requireNonNull(urlValidator, "urlValidator cannot be null");
        this.codeGenerator = Objects.requireNonNull(codeGenerator, "codeGenerator cannot be null");
        this.repository = Objects.requireNonNull(repository, "repository cannot be null");
        this.clock = Objects.requireNonNull(clock, "clock cannot be null");
        this.clickAnalyticsService = Objects.requireNonNull(clickAnalyticsService, "clickAnalyticsService cannot be null");
    }

    public String shortenUrl(String originalUrl) {
        if (!urlValidator.isValid(originalUrl)) {
            throw new IllegalArgumentException("Invalid URL: " + originalUrl);
        }

        for (int attempt = 0; attempt < MAX_GENERATION_ATTEMPTS; attempt++) {
            String shortCode = codeGenerator.generate();

            if (shortCode == null || shortCode.isBlank()) {
                throw new IllegalStateException("Code generator returned an invalid code");
            }

            if (repository.existByShortCode(shortCode)) {
                continue;
            }

            Instant createdAt = clock.instant();
            UrlMapping mapping = new UrlMapping(shortCode, originalUrl, createdAt, null);
            repository.save(mapping);
            return shortCode;
        }

        throw new IllegalStateException("Unable to generate a unique short code");
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
}