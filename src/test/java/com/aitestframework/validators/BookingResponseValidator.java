package com.aitestframework.validators;

import io.restassured.response.Response;
import static org.testng.Assert.*;

public class BookingResponseValidator {

    // Validate successful booking creation
    public static void validateBookingCreated(Response response) {
        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.jsonPath().get("bookingid"));
        assertTrue(response.jsonPath().getInt("bookingid") > 0);
        System.out.println("Booking created successfully with ID: "
                + response.jsonPath().getInt("bookingid"));
    }

    // Validate booking details match expected values
    public static void validateBookingDetails(Response response,
                                              String expectedFirstname, String expectedLastname) {
        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.jsonPath().getString("firstname"), expectedFirstname);
        assertEquals(response.jsonPath().getString("lastname"), expectedLastname);
        System.out.println("Booking details validated successfully");
    }

    // Validate response structure has all required fields
    public static void validateBookingStructure(Response response) {
        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.jsonPath().get("firstname"));
        assertNotNull(response.jsonPath().get("lastname"));
        assertNotNull(response.jsonPath().get("totalprice"));
        assertNotNull(response.jsonPath().get("depositpaid"));
        assertNotNull(response.jsonPath().get("bookingdates"));
        System.out.println("Booking structure validated successfully");
    }

    // Validate error response
    public static void validateErrorResponse(Response response) {
        int status = response.getStatusCode();
        assertTrue(status == 400 || status == 500,
                "Expected error status but got: " + status);
        System.out.println("Error response validated with status: " + status);
    }

    // Validate unauthorized response
    public static void validateUnauthorized(Response response) {
        assertEquals(response.getStatusCode(), 403);
        System.out.println("Unauthorized response validated successfully");
    }

    // Validate not found response
    public static void validateNotFound(Response response) {
        assertEquals(response.getStatusCode(), 404);
        System.out.println("Not found response validated successfully");
    }

    // Validate successful deletion
    public static void validateDeleted(Response response) {
        assertEquals(response.getStatusCode(), 201);
        System.out.println("Booking deleted successfully");
    }
}