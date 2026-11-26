package ch.juventus.mocks.model;

import java.time.LocalDateTime;

/**
 * A movie shown at a specific time in a cinema hall.
 */
public record Screening(long id, String movieTitle, LocalDateTime startTime, int totalSeats, double basePrice) {
}
