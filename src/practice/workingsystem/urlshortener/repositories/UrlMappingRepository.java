package practice.workingsystem.urlshortener.repositories;

import practice.workingsystem.urlshortener.entity.UrlMapping;

import java.util.Optional;

public interface UrlMappingRepository {
    void save(UrlMapping urlMapping);

    Optional<UrlMapping> findByShortCode(String shortCode);

    boolean existByShortCode(String shortCode);

    void deleteByShortCode(String shortCode);
}
