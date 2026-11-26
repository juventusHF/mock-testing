# Exercise 02

**Test class:** `src/test/java/ch/juventus/mocks/exercises/Exercise02Test.java`

## Goal

You can check **that** (and how often) a method of a mock was called.

## Tasks

All tasks test `BookingService`. The setup (mocks and `@InjectMocks`) is already there.

**2.1** Test: `bookTickets_chargesSavesAndSendsConfirmation`  
Given: `screeningRepository.findById(1L)` returns `DUNE` and `priceCalculator.calculateTotal(DUNE, 2)`
returns `36.0`.  
When: `bookingService.bookTickets(1L, "anna@example.com", 2)` is called.  
Then: Verify that:
- `paymentService.charge("anna@example.com", 36.0)` was called
- `bookingRepository.save(...)` was called with the returned booking
- `notificationService.sendBookingConfirmation(...)` was called with the returned booking

Hint: `Booking` has no `equals()`, so `verify` only matches the exact object returned by `bookTickets`.

**2.2** Test: `bookTickets_doesNothing_whenNumberOfSeatsIsInvalid`  
Given: Nothing. Why do you not need any `when(...)` in this test?  
When: `bookingService.bookTickets(1L, "anna@example.com", 0)` is called.  
Then: An `IllegalArgumentException` is thrown, and `screeningRepository`, `bookingRepository`,
`paymentService` and `notificationService` had no interactions (`verifyNoInteractions`).

**2.3** Test: `bookTickets_loadsScreeningOncePerBooking`  
Given: `screeningRepository.findById(1L)` returns `DUNE`.  
When: `bookingService.bookTickets(1L, "anna@example.com", 2)` and
`bookingService.bookTickets(1L, "ben@example.com", 3)` are called.  
Then: Verify that `screeningRepository.findById(1L)` was called exactly **2** times.

**2.4** Test: `cancelBooking_doesNothing_whenBookingIsAlreadyCancelled`  
Given: `bookingRepository.findById("b1")` returns a cancelled booking
(`new Booking("b1", 1, "anna@example.com", 2, 36.0, "TX-1")`, then `booking.cancel()`).  
When: `bookingService.cancelBooking("b1")` is called.  
Then: An `IllegalStateException` is thrown and:
- `paymentService.refund("TX-1", 36.0)` was never called (`never()`)
- `bookingRepository.save(booking)` was never called
- `notificationService` had no interactions

## Reflection

- In 2.1 you could also only *stub* `charge` and not verify it. Why would the test then be weaker?
