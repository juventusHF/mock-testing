# Exercise 02

**Test class:** `src/test/java/ch/juventus/mocks/exercises/Exercise02Test.java`

## Goal

You can check **that** (and how often) a method of a mock was called.

## Tasks

All tasks test `BookingService`. The setup (mocks and `@InjectMocks`) is already there.

**2.1** Test: `bookTickets_chargesSavesAndSendsConfirmation`  
Given: `screeningRepository.findById(1L)` returns `DUNE` and `priceCalculator.calculateTotal(DUNE, 2)`
returns `36.0`.  
When: 2 seats are booked for `"anna@example.com"`.  
Then: Verify that:
- the customer was charged `36.0`
- the returned booking was saved
- a confirmation was sent for it

Hint: `Booking` has no `equals()`, so `verify` only matches the exact object returned by `bookTickets`.

**2.2** Test: `bookTickets_doesNothing_whenNumberOfSeatsIsInvalid`  
Given: Nothing. Why do you not need any `when(...)` in this test?  
When: `0` seats are booked.  
Then: An `IllegalArgumentException` is thrown and **none** of the dependencies was used.

**2.3** Test: `bookTickets_loadsScreeningOncePerBooking`  
Given: The screening repository returns `DUNE` for id `1`.  
When: Two bookings are made for screening `1`.  
Then: Verify that the screening was loaded exactly **2** times.

**2.4** Test: `cancelBooking_doesNothing_whenBookingIsAlreadyCancelled`  
Given: The booking repository returns a cancelled booking for `"b1"`
(`new Booking("b1", 1, "anna@example.com", 2, 36.0, "TX-1")`, then `booking.cancel()`).  
When: `cancelBooking("b1")` is called.  
Then: An `IllegalStateException` is thrown and:
- no refund was made (`never()`)
- the booking was not saved
- no e-mail was sent

## Reflection

- In 2.1 you could also only *stub* `charge` and not verify it. Why would the test then be weaker?
