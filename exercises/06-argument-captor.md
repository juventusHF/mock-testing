# Exercise 06

**Test class:** `src/test/java/ch/juventus/mocks/exercises/Exercise06Test.java`

## Goal

You can capture the arguments a mock was called with and check them in detail.

## Setup

In this test class, `priceCalculator` is a **spy**, so the real prices are calculated:
`DUNE_EVENING` costs `18 + 3 = 21 CHF` per seat.

**6.0** Add captors for `Booking`, `String` and `Double` as fields.

## Tasks

**6.1** Test: `bookTickets_savesBookingWithCorrectData`  
Given: `screeningRepository.findById(2L)` returns `DUNE_EVENING`, and
`paymentService.charge("anna@example.com", 42.0)` returns `"TX-1"`.  
When: `bookingService.bookTickets(2L, "anna@example.com", 2)` is called.  
Then: Capture the booking passed to `bookingRepository.save(...)` and check **all** fields:
- `getId()` is not `null`
- `getScreeningId()` is `2`
- `getCustomerEmail()` is `"anna@example.com"`
- `getSeats()` is `2`
- `getTotalPrice()` is `42.0`
- `getPaymentTransactionId()` is `"TX-1"`
- `getStatus()` is `CONFIRMED`

**6.2** Test: `bookTickets_chargesGroupPriceForEveningScreening`  
Given: `screeningRepository.findById(2L)` returns `DUNE_EVENING`.  
When: `bookingService.bookTickets(2L, "group@example.com", 5)` is called.  
Then: Capture **both** arguments of `paymentService.charge(...)` and check that the e-mail is
`"group@example.com"` and the amount is the correct group price. What is the correct group price?

**6.3** Test: `bookTickets_createsUniqueBookingIds`  
Given: `screeningRepository.findById(2L)` returns `DUNE_EVENING`.  
When: `bookingService.bookTickets(2L, "anna@example.com", 1)` and
`bookingService.bookTickets(2L, "ben@example.com", 3)` are called.  
Then: Capture **all** bookings passed to `bookingRepository.save(...)` (`times(2)` + `getAllValues()`)
and check:
- there are 2
- the first has `getCustomerEmail()` `"anna@example.com"`, the second `"ben@example.com"`
- the two `getId()` values are different

## Reflection

- Compare 6.1 with `argThat` from exercise 05. Which one gives you a better error message if
  the price is wrong? Try it by temporarily expecting `40.0`.
