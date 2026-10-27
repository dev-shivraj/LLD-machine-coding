package practice.workingsystem.urlshortener.client;

import practice.workingsystem.urlshortener.entity.ClickEvent;
import practice.workingsystem.urlshortener.entity.UrlResolutionResult;
import practice.workingsystem.urlshortener.repositories.InMemoryUrlMappingRepository;
import practice.workingsystem.urlshortener.repositories.UrlMappingRepository;
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
        ShortCodeGenerator codeGenerator = new RandomBase62CodeGenerator();
        UrlMappingRepository repository = new InMemoryUrlMappingRepository();
        ClickAnalyticsService analyticsService = new ClickAnalyticsService(clock);

        UrlShortenerService service = new UrlShortenerService(
                urlValidator,
                codeGenerator,
                repository,
                clock,
                analyticsService
        );

        String originalUrl = "https://example.com/products/electronics";

        // Shorten the URL.
        String shortCode = service.shortenUrl(originalUrl);
        System.out.println("Short code: " + shortCode);

        // Resolve it three times.
        for (int i = 1; i <= 3; i++) {
            UrlResolutionResult result = service.resolveShortCode(shortCode);
            System.out.println("Resolution " + i + ": " + result.status());
        }

        // Attempt to resolve an unknown code.
        UrlResolutionResult unknownResult = service.resolveShortCode("unknown123");
        System.out.println("\nUnknown code status: " + unknownResult.status());

        // Display analytics.
        System.out.println("\nTotal clicks: " + analyticsService.getClickCount(shortCode));
        System.out.println("Click events:");

        for (ClickEvent event : analyticsService.getClickEvents(shortCode)) {
            System.out.println(event.shortCode() + " -> " + event.timestamp());
        }

        System.out.println("\nUnknown code clicks: " + analyticsService.getClickCount("unknown123"));
    }
}