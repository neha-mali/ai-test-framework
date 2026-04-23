package com.aitestframework.api;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.ObjectMapper;


import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class BookingApiTest {

    @BeforeClass
    public void setup(){
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    // TestNG runs tests in alphabetical order by default

    // TC01 - Create booking with all fields
    @Test
    public void testCreateBookingWithAllFields() throws Exception{
        Booking booking = new Booking();
        booking.setFirstname("john");
        booking.setLastname("smith");
        booking.setTotalprice(150);
        booking.setDepositpaid(true);
        booking.setBookingdates(new BookingDates("2024-06-01","2024-06-05"));
        booking.setAdditionalneeds("breakfast");

        // Add this to see what JSON is being sent
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Request body: " +
                mapper.writeValueAsString(booking));

        Response response = given()
                .contentType(ContentType.JSON)
                .body(booking)
                .when()
                .post("/booking")
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertNotNull(response.jsonPath().get("bookingid"));
        Assert.assertTrue(response.jsonPath().getInt("bookingid")>0);
        System.out.println("TC01 passed! Booking id:" +response.jsonPath().getInt("bookingid"));
    }

    //TC02 create booking without optional field
    @Test
    public void testCreateBookingWithoutOptionalField(){
        Booking booking = new Booking();
        booking.setFirstname("jane");
        booking.setLastname("doe");
        booking.setTotalprice(100);
        booking.setDepositpaid(false);
        booking.setBookingdates(new BookingDates("2024-07-01","2024-07-05"));

        Response response = given()
                .contentType(ContentType.JSON)
                .body(booking)
                .when()
                .post("/booking")
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertTrue(response.jsonPath().getInt("bookingid")>0);
        System.out.println("TC02 passed! Booking id:" +response.jsonPath().getInt("bookingid"));
    }

    //TC03 create booking with missing firstname
    @Test
    public void testCreateBookingMissingFirstName(){
        Booking booking = new Booking();
        booking.setLastname("doe");
        booking.setTotalprice(100);
        booking.setDepositpaid(false);
        booking.setBookingdates(new BookingDates("2024-07-01","2024-07-05"));

        Response response = given()
                .contentType(ContentType.JSON)
                .body(booking)
                .when()
                .post("/booking")
                .then()
                .extract().response();

        // Note: Restful Booker returns 500 instead of 400 for invalid requests
        // This is a known limitation of this practice API
        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode == 400 || statusCode == 500, "Expected 400 or 500 but got this"+statusCode);
        System.out.println("TC03 status:" +statusCode);
    }

    // TC04 - Empty Request Body
    @Test
    public void testEmptyRequestBody() {
        Response response = given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post("/booking")
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 500);
        System.out.println("TC04 Status: " + response.getStatusCode());
    }

}
