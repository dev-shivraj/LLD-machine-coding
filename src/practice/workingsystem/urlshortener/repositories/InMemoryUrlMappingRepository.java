package practice.workingsystem.urlshortener.repositories;

import practice.workingsystem.urlshortener.UrlMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class InMemoryUrlMappingRepository implements UrlMappingRepository {
    private final Map<String, UrlMapping> mappingByShortCode = new HashMap<>();

    @Override
    public void save(UrlMapping urlMapping) {
        Objects.requireNonNull(urlMapping, "mapping cannot be null");
        mappingByShortCode.put(urlMapping.getShortCode(), urlMapping);
    }

    @Override
    public Optional<UrlMapping> findByShortCode(String shortCode) {
        Objects.requireNonNull(shortCode, "shortCode cannot be null");

        return Optional.ofNullable(mappingByShortCode.get(shortCode));
    }

    @Override
    public boolean existByShortCode(String shortCode) {
        Objects.requireNonNull(shortCode, "shortCode cannot be null");
        return mappingByShortCode.containsKey(shortCode);
    }

    @Override
    public void deleteByShortCode(String shortCode) {
        Objects.requireNonNull(shortCode, "shortCode cannot be null");
        mappingByShortCode.remove(shortCode);
    }
}
