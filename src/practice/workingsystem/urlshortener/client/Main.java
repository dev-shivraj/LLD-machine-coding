package practice.workingsystem.urlshortener.client;

import practice.workingsystem.urlshortener.repositories.InMemoryUrlMappingRepository;
import practice.workingsystem.urlshortener.repositories.UrlMappingRepository;
import practice.workingsystem.urlshortener.service.*;
import practice.workingsystem.urlshortener.strategy.RandomBase62CodeGenerator;
import practice.workingsystem.urlshortener.strategy.ShortCodeGenerator;
import practice.workingsystem.urlshortener.utils.AliasValidator;
import practice.workingsystem.urlshortener.utils.UrlValidator;

import java.time.Duration;
import java.time.Instant;

public class Main {

    public static void main(String[] args) {
        MutableClock clock = new MutableClock(Instant.parse("2026-10-27T10:00:00Z"));

        UrlValidator urlValidator = new UrlValidator();
        AliasValidator aliasValidator = new AliasValidator();
        ShortCodeGenerator codeGenerator = new RandomBase62CodeGenerator();
        UrlMappingRepository repository = new InMemoryUrlMappingRepository();
        ClickAnalyticsService clickAnalyticsService = new ClickAnalyticsService(clock);

        UrlShortenerService service = new UrlShortenerService(
                urlValidator,
                aliasValidator,
                codeGenerator,
                repository,
                clock,
                clickAnalyticsService
        );

        String originalUrl = "https://example.com/offer";

        // 1. Create a generated short code that expires in 10 minutes.
        String generatedCode = service.shortenUrl(originalUrl, Duration.ofMinutes(10));
        System.out.println("Generated code: " + generatedCode);
        System.out.println("Before expiry: " + service.resolveShortCode(generatedCode).status());

        // 2. Create a custom alias that expires in 5 minutes.
        String customAlias = service.shortenUrl(originalUrl, "special-offer", Duration.ofMinutes(5));
        System.out.println("Custom alias: " + customAlias);

        // 3. Successful resolution records a click.
        service.resolveShortCode(customAlias);
        System.out.println("Clicks before expiry: " + clickAnalyticsService.getClickCount(customAlias));

        // 4. Advance time by exactly 5 minutes.
        clock.advance(Duration.ofMinutes(5));
        System.out.println("Alias at expiry: " + service.resolveShortCode(customAlias).status());

        // 5. Expired resolutions must not increment click analytics.
        System.out.println("Clicks after expiry: " + clickAnalyticsService.getClickCount(customAlias));

        // 6. Reclaim the expired alias for a new URL.
        service.shortenUrl("https://example.com/new-offer", customAlias, Duration.ofMinutes(20));

        System.out.println("Reclaimed alias: " + customAlias);
        System.out.println("Clicks after reclamation: " + clickAnalyticsService.getClickCount(customAlias));

        // 7. Advance time until the generated code expires.
        clock.advance(Duration.ofMinutes(5));
        System.out.println("Generated code after 10 minutes: " + service.resolveShortCode(generatedCode).status());

        // 8. Advance to the custom alias's new expiration time.
        clock.advance(Duration.ofMinutes(15));
        System.out.println("Reclaimed alias at expiry: " + service.resolveShortCode(customAlias).status());
    }
}