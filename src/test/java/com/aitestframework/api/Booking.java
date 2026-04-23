package com.aitestframework.api;

public class Booking {

    private String firstname;
    private String lastname;
    private int totalprice;
    private boolean depositpaid;

    // in this class we have filed bookingDates whose type is BookingDates and when you calling then object create
    private BookingDates bookingdates;
    private String additionalneeds;

    public String getFirstname(){

        return firstname;
    }

    public String getLastname(){

        return lastname;
    }

    public int getTotalprice(){

        return totalprice;
    }

    public boolean getDepositpaid(){

        return depositpaid;
    }

    // here bookingdate is object which has 2 values that's why we have return in that way
    public BookingDates getBookingdates(){
        return bookingdates;
    }

    public String getAdditionalneeds(){

        return additionalneeds;
    }

    public void setFirstname(String firstname){
        this.firstname = firstname;
    }

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public void setTotalprice(int totalprice){
        this.totalprice = totalprice;
    }

    public void setDepositpaid(boolean depositpaid){
        this.depositpaid = depositpaid;
    }

    public void setBookingdates(BookingDates bookingdates){
        this.bookingdates = bookingdates;
    }

    public void setAdditionalneeds(String additionalneeds){
        this.additionalneeds = additionalneeds;
    }
}

/*
{
    "firstname" : "Jim",
    "lastname" : "Brown",
    "totalprice" : 111,
    "depositpaid" : true,
    "bookingdates" : {
        "checkin" : "2018-01-01",
        "checkout" : "2019-01-01"
    },
    "additionalneeds" : "Breakfast"
}'
 */