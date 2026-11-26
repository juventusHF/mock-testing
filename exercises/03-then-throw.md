# Exercise 03

**Test class:** `src/test/java/ch/juventus/mocks/exercises/Exercise03Test.java`

## Goal

You can simulate errors in dependencies and test how the class under test reacts to them.

## Tasks

Look at `PaymentService`: `charge` returns a transaction id, `refund` is `void`. Both throw a
`PaymentFailedException` if something goes wrong.

**3.1** Test: `bookTickets_throwsExceptionAndSendsNoMail_whenPaymentFails`  
Given: The screening repository returns `DUNE` for id `1`, the price for 2 seats is `36.0` and
`charge("anna@example.com", 36.0)` throws `new PaymentFailedException("Card declined")`.  
When: 2 seats are booked for `"anna@example.com"`.  
Then:
- `bookTickets` throws the exception (check the message too)
- no confirmation e-mail was sent

**3.2** Test: `bookTickets_secondAttemptSucceeds_afterFirstPaymentFailed`  
Given: Same screening and price as in 3.1. The first `charge` throws a `PaymentFailedException("Timeout")`,
the second returns `"TX-2"`.  
When: `bookTickets` is called twice.  
Then: The first call fails, the second returns a booking with transaction id `"TX-2"`.

**3.3** Test: `cancelBooking_keepsBookingConfirmed_whenRefundFails`  
Given: The booking repository returns `new Booking("b1", 1, "anna@example.com", 2, 36.0, "TX-1")` for
`"b1"`, and `refund("TX-1", 36.0)` throws a `PaymentFailedException`.  
When: `cancelBooking("b1")` is called.  
Then: A `PaymentFailedException` is thrown and:
- the booking is still `CONFIRMED`
- the booking was not saved and no e-mail was sent

Hint: `refund` is `void`, so `when(...)` doesn't compile. Use `doThrow(...).when(mock).method(...)`.

## Reflection

- Look at `bookTickets`: if the **confirmation e-mail** fails, the exception is caught. If the
  **payment** fails, it isn't. Why does that make sense?
