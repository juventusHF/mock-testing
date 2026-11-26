# Mock Testing with Mockito – Cinema Ticket Booking

A small cinema ticket booking application to practice unit testing with **Mockito**.

## The domain

A cinema shows **screenings** (a movie at a specific time). Customers **book** seats for a screening, pay
online and receive a confirmation e-mail. Bookings can be cancelled, which refunds the money.

```
src/main/java/ch/juventus/mocks
├── model
│   ├── Screening            record: id, movieTitle, startTime, totalSeats, basePrice
│   ├── Booking              id, screeningId, customerEmail, seats, totalPrice, paymentTransactionId, status
│   └── BookingStatus        CONFIRMED | CANCELLED
├── repository
│   ├── ScreeningRepository  (interface)  + InMemoryScreeningRepository (with sample data)
│   └── BookingRepository    (interface)  + InMemoryBookingRepository
├── external                 "the outside world" – we never want to call these in a unit test
│   ├── PaymentService       charge(email, amount) -> transactionId, refund(transactionId, amount)
│   └── NotificationService  sendBookingConfirmation(booking), sendCancellationConfirmation(booking)
├── service
│   ├── ScreeningService     getScreening(id), findByTitle(searchText)
│   ├── BookingService       getAvailableSeats(screeningId), bookTickets(...), cancelBooking(bookingId)
│   └── PriceCalculator      calculateSeatPrice(screening), calculateTotal(screening, seats)
└── exception                ScreeningNotFound, BookingNotFound, NotEnoughSeats, PaymentFailed
```

### Business rules

| Rule | Where |
|---|---|
| A booking has 1 to 10 seats | `BookingService.bookTickets` |
| You can only book if enough seats are free (cancelled bookings don't count) | `BookingService.bookTickets` |
| Screenings starting at 20:00 or later cost 3 CHF more per seat | `PriceCalculator` |
| 5 or more seats get a 10% group discount | `PriceCalculator` |
| Booking order: charge → save → send confirmation e-mail | `BookingService.bookTickets` |
| If the confirmation e-mail fails, the booking is still valid | `BookingService.bookTickets` |
| Cancelling: refund → mark cancelled → save → send e-mail | `BookingService.cancelBooking` |
| A cancelled booking can't be cancelled again | `BookingService.cancelBooking` |

Read `BookingService` before you start. It's short, and you need to know what it does to test it.

## Exercises

| # | Topic | Instructions | Test class |
|---|---|---|---|
| 1 | `@Mock`, `@InjectMocks`, `when().thenReturn()` | [01-mock-and-injectmocks.md](exercises/01-mock-and-injectmocks.md) | `Exercise01Test` |
| 2 | `verify()` | [02-verify.md](exercises/02-verify.md) | `Exercise02Test` |
| 3 | `when().thenThrow()`, `doThrow()` | [03-then-throw.md](exercises/03-then-throw.md) | `Exercise03Test` |
| 4 | `@Spy`, `doReturn().when()` | [04-spy.md](exercises/04-spy.md) | `Exercise04Test` |
| 5 | Argument matchers | [05-argument-matchers.md](exercises/05-argument-matchers.md) | `Exercise05Test` |
| 6 | `ArgumentCaptor` | [06-argument-captor.md](exercises/06-argument-captor.md) | `Exercise06Test` |

The test skeletons are in `src/test/java/ch/juventus/mocks/exercises`. Every test starts with
`fail("TODO ...")`. Replace it with your solution until all tests are green.

## Running the tests

```bash
./mvnw test                         # all tests
./mvnw test -Dtest=Exercise01Test   # a single exercise
```

Or run them directly in your IDE.
