# Exercise 05

**Test class:** `src/test/java/ch/juventus/mocks/exercises/Exercise05Test.java`

## Goal

You can stub and verify calls without knowing the exact argument values.

## Tasks

**5.1** Test: `bookTickets_usesTransactionIdFromPaymentService`  
Given: Use matchers for all stubs:
- `screeningRepository.findById(...)` returns `DUNE` for any id (`anyLong()`)
- `priceCalculator.calculateTotal(...)` returns `50.0` for any screening and any number of seats
- `paymentService.charge(...)` returns `"TX-42"` for any e-mail and any amount

When: `bookingService.bookTickets(1L, "anna@example.com", 3)` is called.  
Then:
- the returned booking's `getPaymentTransactionId()` is `"TX-42"`
- `notificationService.sendBookingConfirmation(...)` was called with **any** `Booking`

**5.2** Test: `bookTickets_chargesTheCustomer_matchersMustBeUsedForAllArguments`  
Given: `screeningRepository.findById(...)` returns `DUNE` for any id.  
When: `bookingService.bookTickets(1L, "anna@example.com", 2)` is called.  
Then: Verify `paymentService.charge(...)` twice:
1. with exactly `"anna@example.com"` and **any** amount. First try it without `eq(...)` and read the
   error message.
2. with **any** e-mail that ends with `@example.com` and any amount

Hint: if you use a matcher for one argument, you must use matchers for **all** arguments.

**5.3** Test: `bookTickets_savesConfirmedBookingWithRequestedSeats`  
Given: `screeningRepository.findById(...)` returns `DUNE` for any id.  
When: `bookingService.bookTickets(1L, "anna@example.com", 3)` is called.  
Then: Use `argThat` to verify that `bookingRepository.save(...)` was called with a booking that has
3 seats **and** is confirmed.

## Reflection

- `any()` can make tests too loose. Find an example where `any()` would let a bug slip through.
