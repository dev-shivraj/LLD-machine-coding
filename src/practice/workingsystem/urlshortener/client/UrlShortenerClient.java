package practice.workingsystem.urlshortener.client;

import practice.workingsystem.urlshortener.entity.ClickEvent;
import practice.workingsystem.urlshortener.entity.UrlResolutionResult;
import practice.workingsystem.urlshortener.repositories.InMemoryUrlMappingRepository;
import practice.workingsystem.urlshortener.repositories.UrlMappingRepository;
import practice.workingsystem.urlshortener.service.AliasValidator;
import practice.workingsystem.urlshortener.service.ClickAnalyticsService;
import practice.workingsystem.urlshortener.service.UrlShortenerService;
import practice.workingsystem.urlshortener.service.UrlValidator;
import practice.workingsystem.urlshortener.strategy.RandomBase62CodeGenerator;
import practice.workingsystem.urlshortener.strategy.ShortCodeGenerator;

import java.time.Clock;

public class UrlShortenerClient {

    public static void main(String[] args) {
        Clock clock = Clock.systemUTC();
        UrlValidator urlValidator = new UrlValidator();
        AliasValidator aliasValidator = new AliasValidator();
        ShortCodeGenerator codeGenerator = new RandomBase62CodeGenerator();
        UrlMappingRepository repository = new InMemoryUrlMappingRepository();
        ClickAnalyticsService analyticsService = new ClickAnalyticsService(clock);

        UrlShortenerService service = new UrlShortenerService(
                urlValidator,
                aliasValidator,
                codeGenerator,
                repository,
                clock,
                analyticsService
        );

        String originalUrl = "https://example.com/products/electronics";

        // Generate a random short code.
        String generatedCode = service.shortenUrl(originalUrl);
        System.out.println("Generated short URL: https://short.ly/" + generatedCode);

        // Create a custom alias.
        String customAlias = service.shortenUrl("https://example.com/products", "electronics");
        System.out.println("Custom short URL: https://short.ly/" + customAlias);

        // Resolve the custom alias.
        UrlResolutionResult result = service.resolveShortCode(customAlias);

        System.out.println("Resolution status: " + result.status());
        System.out.println("Original URL: " + result.originalUrl());

        // Display analytics.
        System.out.println("Custom alias clicks: " + analyticsService.getClickCount(customAlias));

        // Demonstrate duplicate alias rejection.
        try {
            service.shortenUrl("https://example.com/another-product", "electronics");
        } catch (IllegalArgumentException exception) {
            System.out.println("Duplicate alias rejected: " + exception.getMessage());
        }
    }
}