# Exercise 05

**Test class:** `src/test/java/ch/juventus/mocks/exercises/Exercise05Test.java`

## Goal

You can stub and verify calls without knowing the exact argument values.

## Tasks

**5.1** Test: `bookTickets_usesTransactionIdFromPaymentService`  
Given: Use matchers for all stubs:
- any screening id → `DUNE`
- `calculateTotal` for any screening and any number of seats → `50.0`
- `charge` for any e-mail and any amount → `"TX-42"`

When: 3 seats are booked.  
Then: The booking has transaction id `"TX-42"`, and a confirmation was sent for **any** `Booking`.

**5.2** Test: `bookTickets_chargesTheCustomer_matchersMustBeUsedForAllArguments`  
Given: Any screening id returns `DUNE`.  
When: 2 seats are booked for `"anna@example.com"`.  
Then: Verify the `charge` call with:
1. the exact e-mail and **any** amount. First try it without `eq(...)` and read the error message.
2. **any** e-mail that ends with `@example.com` and any amount

Hint: if you use a matcher for one argument, you must use matchers for **all** arguments.

**5.3** Test: `bookTickets_savesConfirmedBookingWithRequestedSeats`  
Given: Any screening id returns `DUNE`.  
When: 3 seats are booked.  
Then: Use `argThat` to verify that `save` was called with a booking that has 3 seats **and** is
confirmed.

## Reflection

- `any()` can make tests too loose. Find an example where `any()` would let a bug slip through.
