# Exercise 04

**Test class:** `src/test/java/ch/juventus/mocks/exercises/Exercise04Test.java`

## Goal

You know the difference between a mock and a spy, can partially stub a real object and know why
`doReturn().when()` is needed for spies.

## Tasks

**4.0** Turn the fields `priceCalculator` and `bookingRepository` into spies.
`InMemoryBookingRepository` is the real in-memory repository. `@InjectMocks` injects both spies into
`bookingService`.

### Part A – spy on `PriceCalculator`

Read the rules in `PriceCalculator` first.

**4.1** Test: `calculateTotal_appliesGroupDiscount_withStubbedSeatPrice`  
Given: `priceCalculator.calculateSeatPrice(DUNE_AFTERNOON)` is stubbed with `doReturn` to return `10.0`,
so we test **only** the group discount, independent of the seat price rules.  
When: `priceCalculator.calculateTotal(DUNE_AFTERNOON, 5)` is called.  
Then: The result is `45.0`.

**4.2** Test: `whenThenReturn_callsTheRealMethodOnASpy`  
Given: Nothing. This test shows a pitfall.  
When / Then:
1. Write `when(priceCalculator.calculateSeatPrice(null)).thenReturn(10.0)` inside an
   `assertThrows(NullPointerException.class, ...)`. Why is a `NullPointerException` thrown?
2. Stub `priceCalculator.calculateSeatPrice(null)` with `doReturn(10.0)`. Then
   `priceCalculator.calculateSeatPrice(null)` returns `10.0`.

### Part B – spies injected into `BookingService`

**4.3** Test: `bookTickets_chargesRealPrice_forEveningScreening`  
Given: `screeningRepository.findById(2L)` returns `DUNE_EVENING`.  
When: `bookingService.bookTickets(2L, "anna@example.com", 2)` is called.  
Then: Verify that:
- `priceCalculator.calculateTotal(DUNE_EVENING, 2)` was called on the spy
- `paymentService.charge("anna@example.com", 42.0)` was called with the **real** price
  ((18 + 3 evening surcharge) × 2)

**4.4** Test: `bookTickets_throwsException_whenRepositoryReportsFullScreening`  
Given: `screeningRepository.findById(1L)` returns `DUNE_AFTERNOON`. Override **one** method of the real
repository: `bookingRepository.findByScreeningId(1L)` returns a list with one booking for all 80 seats.  
When: `bookingService.bookTickets(1L, "anna@example.com", 1)` is called.  
Then: A `NotEnoughSeatsException` is thrown.

## Reflection

- When would you use a real in-memory repository (a **fake**) instead of a mock?
