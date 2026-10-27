package practice.workingsystem.urlshortener.client;

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

        repository.findByShortCode(shortCode)
                .ifPresent(mapping -> System.out.println("Stored original URL: " + mapping.getOriginalUrl()));
    }
}