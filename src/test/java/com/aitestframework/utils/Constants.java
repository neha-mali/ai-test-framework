package com.aitestframework.utils;

public class Constants {

    // API Base URL
    public static final String BASE_URL =
            "https://restful-booker.herokuapp.com";

    // Auth credentials
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "password123";

    // Test IDs
    public static final String INVALID_ID = "999999";
    public static final String STRING_ID = "abc";

    // Booking defaults
    public static final String DEFAULT_FIRSTNAME = "John";
    public static final String DEFAULT_LASTNAME = "Smith";
    public static final int DEFAULT_PRICE = 150;
    public static final String DEFAULT_CHECKIN = "2024-06-01";
    public static final String DEFAULT_CHECKOUT = "2024-06-05";
    public static final String DEFAULT_ADDITIONAL_NEEDS = "Breakfast";

    // Updated booking values
    public static final String UPDATED_FIRSTNAME = "Jane";
    public static final String UPDATED_LASTNAME = "Doe";
    public static final int UPDATED_PRICE = 200;
    public static final String UPDATED_ADDITIONAL_NEEDS = "Lunch";
}





/*
Constants
- BASE_URL = "https://..."
- ADMIN_USERNAME = "admin"
        ↓
BaseTest uses Constants
- requestSpec uses BASE_URL
- Sets up HTTP template
        ↓
BookingApiTest extends BaseTest
- inherits requestSpec
- uses BookingRequestBuilder for test data
- uses BookingResponseValidator for assertions
        ↓
Test runs:
given().spec(requestSpec)           ← from BaseTest
    .body(new BookingRequestBuilder().build()) ← from Builder
    .post("/booking")
BookingResponseValidator.validateBookingCreated(response) ← from Validator
 */
