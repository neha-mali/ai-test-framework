package com.aitestframework.builders;
import com.aitestframework.utils.Constants;

import com.aitestframework.api.Booking;
import com.aitestframework.api.BookingDates;

public class BookingRequestBuilder {

    // Default values
    private String firstname = Constants.DEFAULT_FIRSTNAME;
    private String lastname = Constants.DEFAULT_LASTNAME;
    private int totalprice = Constants.DEFAULT_PRICE;
    private boolean depositpaid = true;
    private String checkin = Constants.DEFAULT_CHECKIN;
    private String checkout = Constants.DEFAULT_CHECKOUT;
    private String additionalneeds = Constants.DEFAULT_ADDITIONAL_NEEDS;

    // Builder methods
    public BookingRequestBuilder withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public BookingRequestBuilder withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public BookingRequestBuilder withTotalprice(int totalprice) {
        this.totalprice = totalprice;
        return this;
    }

    public BookingRequestBuilder withDepositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
        return this;
    }

    public BookingRequestBuilder withCheckin(String checkin) {
        this.checkin = checkin;
        return this;
    }

    public BookingRequestBuilder withCheckout(String checkout) {
        this.checkout = checkout;
        return this;
    }

    public BookingRequestBuilder withAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
        return this;
    }

    public BookingRequestBuilder withoutAdditionalneeds() {
        this.additionalneeds = null;
        return this;
    }

    public Booking build() {
        Booking booking = new Booking();
        booking.setFirstname(firstname);
        booking.setLastname(lastname);
        booking.setTotalprice(totalprice);
        booking.setDepositpaid(depositpaid);
        booking.setBookingdates(new BookingDates(checkin, checkout));
        booking.setAdditionalneeds(additionalneeds);
        return booking;
    }
}

/*
In order to avoid constructor with various parameter we are making it more readable
// Hard to read — what does each value mean?
Booking booking = new Booking("John", "Smith", 150, true, "2024-06-01", "2024-06-05", "Breakfast");

Builder pattern:
// Clean, readable, only specify what you need
Booking booking = new BookingRequestBuilder()
    .withFirstname("John")
    .withTotalprice(200)
    .build();

Builder pattern has default values and we just override default


Start with base (defaults)
        ↓
Add piece 1 (withFirstname)
        ↓
Add piece 2 (withTotalprice)
        ↓
Add piece 3 (withCheckin)
        ↓
Finalize (build)
        ↓
Complete object ready!
 */


/*
BUILDER:
firstname = "John"  ← default
lastname = "Smith"  ← default
totalprice = 150    ← default

.withFirstname("Jane")
public BookingRequestBuilder withFirstname(String firstname) {
    this.firstname = "Jane"; // updates BUILDER
    return this; // hands BUILDER back
}

BUILDER:
firstname = "Jane"  ← updated!
lastname = "Smith"  ← still default
totalprice = 150    ← still default
 */