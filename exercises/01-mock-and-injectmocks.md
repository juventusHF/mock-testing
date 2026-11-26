# Exercise 01

**Test class:** `src/test/java/ch/juventus/mocks/exercises/Exercise01Test.java`

## Goal

You can create mocks with annotations, let Mockito inject them into the class under test and define
what a mock returns.

## Tasks

### Setup

**1.1** Add the annotation that enables Mockito for JUnit 5 to the test class.

**1.2** Mark the five dependency fields as mocks.

**1.3** Mark `screeningService` and `bookingService` so that Mockito creates them and injects the mocks.

### Tests

**1.4** Test: `getScreening_returnsScreeningFromRepository`  
Given: The repository returns `DUNE_AFTERNOON` for id `1`.  
When: `getScreening(1)` is called.  
Then: It returns exactly this screening.

**1.5** Test: `getScreening_throwsException_whenScreeningDoesNotExist`  
Given: The repository finds nothing for id `99` (what does `findById` return then?).  
When: `getScreening(99)` is called.  
Then: A `ScreeningNotFoundException` is thrown. Use `assertThrows`.

**1.6** Test: `findByTitle_returnsOnlyMatchingScreenings_ignoringCase`  
Given: The repository contains `DUNE_AFTERNOON`, `INSIDE_OUT` and `DUNE_EVENING`.  
When: `findByTitle("dune")` is called (lower case!).  
Then: It returns the two Dune screenings.

**1.7** Test: `getAvailableSeats_subtractsConfirmedBookings`  
Given: The screening repository returns `DUNE_AFTERNOON` (80 seats) for id `1`, and the booking repository
returns two bookings with 3 and 4 seats for this screening.  
When: `getAvailableSeats(1)` is called.  
Then: It returns `73`.  
Hint: `new Booking("b1", 1, "anna@example.com", 3, 54.0, "TX-1")` creates a confirmed booking.

## Reflection

- Why do we mock the `ScreeningRepository` **interface** and not use `InMemoryScreeningRepository`?
- `ScreeningService` only needs a `ScreeningRepository`. What does Mockito do with the other mocks?
