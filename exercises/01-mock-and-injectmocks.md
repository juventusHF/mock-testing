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
Given: `screeningRepository.findById(1L)` returns `DUNE_AFTERNOON`.  
When: `screeningService.getScreening(1L)` is called.  
Then: The result is exactly `DUNE_AFTERNOON`.

**1.5** Test: `getScreening_throwsException_whenScreeningDoesNotExist`  
Given: `screeningRepository.findById(99L)` finds nothing (what does `findById` return then?).  
When: `screeningService.getScreening(99L)` is called.  
Then: A `ScreeningNotFoundException` is thrown. Use `assertThrows`.

**1.6** Test: `findByTitle_returnsOnlyMatchingScreenings_ignoringCase`  
Given: `screeningRepository.findAll()` returns `DUNE_AFTERNOON`, `INSIDE_OUT` and `DUNE_EVENING`.  
When: `screeningService.findByTitle("dune")` is called (lower case!).  
Then: The result contains exactly `DUNE_AFTERNOON` and `DUNE_EVENING`.

**1.7** Test: `getAvailableSeats_subtractsConfirmedBookings`  
Given: `screeningRepository.findById(1L)` returns `DUNE_AFTERNOON` (80 seats), and
`bookingRepository.findByScreeningId(1L)` returns two confirmed bookings with 3 and 4 seats.  
When: `bookingService.getAvailableSeats(1L)` is called.  
Then: The result is `73`.  
Hint: `new Booking("b1", 1, "anna@example.com", 3, 54.0, "TX-1")` creates a confirmed booking.

## Reflection

- Why do we mock the `ScreeningRepository` **interface** and not use `InMemoryScreeningRepository`?
- `ScreeningService` only needs a `ScreeningRepository`. What does Mockito do with the other mocks?
