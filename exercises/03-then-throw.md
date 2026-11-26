# Exercise 03

**Test class:** `src/test/java/ch/juventus/mocks/exercises/Exercise03Test.java`

## Goal

You can simulate errors in dependencies and test how the class under test reacts to them.

## Tasks

Look at `PaymentService`: `charge` returns a transaction id, `refund` is `void`. Both throw a
`PaymentFailedException` if something goes wrong.

**3.1** Test: `bookTickets_throwsExceptionAndSendsNoMail_whenPaymentFails`  
Given: `screeningRepository.findById(1L)` returns `DUNE`, `priceCalculator.calculateTotal(DUNE, 2)`
returns `36.0` and `paymentService.charge("anna@example.com", 36.0)` throws
`new PaymentFailedException("Card declined")`.  
When: `bookingService.bookTickets(1L, "anna@example.com", 2)` is called.  
Then:
- a `PaymentFailedException` with the message `"Card declined"` is thrown
- `notificationService` had no interactions

**3.2** Test: `bookTickets_secondAttemptSucceeds_afterFirstPaymentFailed`  
Given: `screeningRepository.findById(1L)` returns `DUNE`, `priceCalculator.calculateTotal(DUNE, 2)`
returns `36.0`. `paymentService.charge("anna@example.com", 36.0)` throws
`new PaymentFailedException("Timeout")` on the first call and returns `"TX-2"` on the second call.  
When: `bookingService.bookTickets(1L, "anna@example.com", 2)` is called twice.  
Then: The first call throws a `PaymentFailedException`, the second returns a booking with
`getPaymentTransactionId()` equal to `"TX-2"`.

**3.3** Test: `cancelBooking_keepsBookingConfirmed_whenRefundFails`  
Given: `bookingRepository.findById("b1")` returns
`new Booking("b1", 1, "anna@example.com", 2, 36.0, "TX-1")`, and `paymentService.refund("TX-1", 36.0)`
throws `new PaymentFailedException("Refund rejected")`.  
When: `bookingService.cancelBooking("b1")` is called.  
Then: A `PaymentFailedException` is thrown and:
- `booking.getStatus()` is still `CONFIRMED`
- `bookingRepository.save(booking)` was never called
- `notificationService` had no interactions

Hint: `refund` is `void`, so `when(...)` doesn't compile. Use `doThrow(...).when(mock).method(...)`.

## Reflection

- Look at `bookTickets`: if the **confirmation e-mail** fails, the exception is caught. If the
  **payment** fails, it isn't. Why does that make sense?
