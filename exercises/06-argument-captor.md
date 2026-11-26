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
Given: The screening repository returns `DUNE_EVENING` for id `2`, and `charge("anna@example.com", 42.0)`
returns `"TX-1"`.  
When: 2 seats are booked for `"anna@example.com"`.  
Then: Capture the saved booking and check **all** fields: id is not `null`, screening id, e-mail,
seats, total price, transaction id and status.

**6.2** Test: `bookTickets_chargesGroupPriceForEveningScreening`  
Given: The screening repository returns `DUNE_EVENING` for id `2`.  
When: 5 seats are booked for `"group@example.com"`.  
Then: Capture **both** arguments of `charge` (e-mail and amount) and check them. What is the correct
group price?

**6.3** Test: `bookTickets_createsUniqueBookingIds`  
Given: The screening repository returns `DUNE_EVENING` for id `2`.  
When: Two bookings are made (`"anna@example.com"` 1 seat, `"ben@example.com"` 3 seats).  
Then: Capture **all** saved bookings (`times(2)` + `getAllValues()`) and check:
- there are 2
- the e-mails are in the right order
- the two ids are different

## Reflection

- Compare 6.1 with `argThat` from exercise 05. Which one gives you a better error message if
  the price is wrong? Try it by temporarily expecting `40.0`.
