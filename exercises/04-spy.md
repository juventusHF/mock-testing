# Exercise 04

**Test class:** `src/test/java/ch/juventus/mocks/exercises/Exercise04Test.java`

## Goal

You know the difference between a mock and a spy, can partially stub a real object and know why
`doReturn().when()` is needed for spies.

## Tasks

**4.0** Turn the fields `priceCalculator` and `bookingRepository` into spies.
`InMemoryBookingRepository` is the real in-memory repository. `@InjectMocks` injects both spies into
`BookingService`.

### Part A – spy on `PriceCalculator`

Read the rules in `PriceCalculator` first.

**4.1** Test: `calculateTotal_appliesGroupDiscount_withStubbedSeatPrice`  
Given: `calculateSeatPrice(DUNE_AFTERNOON)` is stubbed to return `10.0`, so we test **only** the group
discount, independent of the seat price rules.  
When: `calculateTotal(DUNE_AFTERNOON, 5)` is called.  
Then: It returns `45.0`.

**4.2** Test: `whenThenReturn_callsTheRealMethodOnASpy`  
Given: Nothing. This test shows a pitfall.  
When / Then:
1. Write `when(priceCalculator.calculateSeatPrice(null)).thenReturn(10.0)` inside an
   `assertThrows(NullPointerException.class, ...)`. Why is a `NullPointerException` thrown?
2. Stub the same call with `doReturn`. Then `calculateSeatPrice(null)` returns `10.0`.

### Part B – spies injected into `BookingService`

**4.3** Test: `bookTickets_chargesRealPrice_forEveningScreening`  
Given: The screening repository returns `DUNE_EVENING` for id `2`.  
When: 2 seats are booked.  
Then: Verify that `calculateTotal(DUNE_EVENING, 2)` was called on the spy and that the customer was
charged the **real** price `42.0` ((18 + 3 evening surcharge) × 2).

**4.4** Test: `bookTickets_throwsException_whenRepositoryReportsFullScreening`  
Given: The screening repository returns `DUNE_AFTERNOON` for id `1`. Override **one** method of the real
booking repository: `findByScreeningId(1L)` returns a list with a booking for all 80 seats.  
When: 1 more seat is booked.  
Then: A `NotEnoughSeatsException` is thrown.

## Reflection

- When would you use a real in-memory repository (a **fake**) instead of a mock?
