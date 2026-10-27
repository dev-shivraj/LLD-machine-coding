package practice.workingsystem.urlshortener.client;

import practice.workingsystem.urlshortener.entity.UrlResolutionResult;
import practice.workingsystem.urlshortener.repositories.InMemoryUrlMappingRepository;
import practice.workingsystem.urlshortener.repositories.UrlMappingRepository;
import practice.workingsystem.urlshortener.service.UrlShortenerService;
import practice.workingsystem.urlshortener.service.UrlValidator;
import practice.workingsystem.urlshortener.strategy.RandomBase62CodeGenerator;
import practice.workingsystem.urlshortener.strategy.ShortCodeGenerator;

import java.time.Clock;

public class UrlShortenerClient {

    public static void main(String[] args) {
        UrlValidator urlValidator = new UrlValidator();
        ShortCodeGenerator codeGenerator = new RandomBase62CodeGenerator();
        UrlMappingRepository repository = new InMemoryUrlMappingRepository();
        UrlShortenerService service = new UrlShortenerService(urlValidator, codeGenerator, repository, Clock.systemUTC());

        String originalUrl = "https://example.com/products/electronics";
        String shortCode = service.shortenUrl(originalUrl);
        System.out.println("Short code: " + shortCode);
        System.out.println("Short URL: https://short.ly/" + shortCode);

        // Resolve the generated short code.
        UrlResolutionResult result = service.resolveShortCode(shortCode);
        System.out.println("\nResolution status: " + result.status());
        System.out.println("Original URL: " + result.originalUrl());

        // Resolve a code that does not exist.
        UrlResolutionResult unknownResult = service.resolveShortCode("unknown123");
        System.out.println("\nUnknown code status: " + unknownResult.status());
    }
}