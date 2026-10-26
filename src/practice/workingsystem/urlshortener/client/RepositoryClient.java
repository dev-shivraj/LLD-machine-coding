package practice.workingsystem.urlshortener.client;

import practice.workingsystem.urlshortener.UrlMapping;
import practice.workingsystem.urlshortener.repositories.InMemoryUrlMappingRepository;
import practice.workingsystem.urlshortener.repositories.UrlMappingRepository;

import java.time.Instant;

public class RepositoryClient {

    public static void main(String[] args) {
        UrlMappingRepository repository = new InMemoryUrlMappingRepository();
        UrlMapping mapping = new UrlMapping("aZ81kLm", "https://example.com/products", Instant.now(), null);

        repository.save(mapping);
        System.out.println("Exists: " + repository.existByShortCode("aZ81kLm"));

        repository.findByShortCode("aZ81kLm")
                .ifPresent(found -> System.out.println(found.getOriginalUrl()));

        repository.deleteByShortCode("aZ81kLm");
        System.out.println("Exists after deletion: " + repository.existByShortCode("aZ81kLm"));
    }
}