package practice.workingsystem.urlshortener.service;

import practice.workingsystem.urlshortener.entity.ClickEvent;

import java.time.Clock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ClickAnalyticsService {

    private final Clock clock;

    private final Map<String, List<ClickEvent>> eventsByShortCode = new HashMap<>();

    public ClickAnalyticsService(Clock clock) {
        this.clock = Objects.requireNonNull(clock, "clock cannot be null");
    }

    public void recordClick(String shortCode) {
        ClickEvent event = new ClickEvent(shortCode, clock.instant());
        eventsByShortCode.computeIfAbsent(shortCode, key -> new ArrayList<>()).add(event);
    }

    public int getClickCount(String shortCode) {
        return eventsByShortCode
                .getOrDefault(shortCode, List.of())
                .size();
    }

    public List<ClickEvent> getClickEvents(String shortCode) {
        return List.copyOf(eventsByShortCode.getOrDefault(shortCode, List.of()));
    }

    public void deleteAnalytics(String shortCode) {
        Objects.requireNonNull(shortCode, "shortCode cannot be null");
        eventsByShortCode.remove(shortCode);
    }
}