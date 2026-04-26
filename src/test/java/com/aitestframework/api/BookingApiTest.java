package com.aitestframework.api;
import com.aitestframework.utils.RetryAnalyzer;
import io.restassured.response.Response;
import com.aitestframework.utils.Constants;
import com.aitestframework.base.BaseTest;
import com.aitestframework.utils.RetryAnalyzer;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import com.aitestframework.builders.BookingRequestBuilder;
import com.aitestframework.validators.BookingResponseValidator;
import static io.restassured.RestAssured.given;

@Epic("Restful Booker API") // allure project name top level
@Feature("Booking Management") // allure second level
public class BookingApiTest extends BaseTest {


    // ==================== HELPER METHOD ====================
    // only one thread can execute this method at a time
    // as we are running 3 methods in parallel
    /*
    Thread 1: createFreshBooking() starts
    Thread 2: createFreshBooking() starts  ← at same time!
    Thread 3: createFreshBooking() starts  ← at same time!

    All 3 hit API simultaneously
    API gets confused
    Returns wrong booking IDs
    Tests fail unpredictably!
     */
    private synchronized int createFreshBooking() {
        Booking booking = new BookingRequestBuilder().build();

        Response response = given()
                .spec(requestSpec)
                .body(booking)
                .when()
                .post("/booking")
                .then()
                .extract().response();

        int id = response.jsonPath().getInt("bookingid");
        System.out.println("Created fresh booking ID: " + id);
        return id;
    }

    // ==================== POST TESTS ====================

    // TestNG runs tests in alphabetical order by default
    // TC01 - Create booking with all fields
    // if we know which we want to retry we can add in this way
    //@Test(retryAnalyzer = RetryAnalyzer.class)
    @Test
    @Story("Create Booking")
    @Description("Create a booking with all valid fields")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateBookingWithAllFields() throws Exception{
        Booking booking = new BookingRequestBuilder().build();

        Response response = given()
                .spec(requestSpec)
                .body(booking) // here RestAssured uses Jackson internally to convert to string
                .when()
                .post("/booking")
                .then()
                .extract().response();

        BookingResponseValidator.validateBookingCreated(response);
        System.out.println("TC01 passed!");
    }

    //TC02 create booking without optional field
    @Test
    @Story("Create Booking")
    @Description("Create booking without optional additionalneeds field")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateBookingWithoutOptionalField(){
        Booking booking = new BookingRequestBuilder()
                .withAdditionalneeds(null)
                .build();

        Response response = given()
                .spec(requestSpec)
                .body(booking)
                .when()
                .post("/booking")
                .then()
                .extract().response();

        BookingResponseValidator.validateBookingCreated(response);
        System.out.println("TC02 passed!");
    }

    //TC03 create booking with missing firstname
    @Test
    @Story("Create Booking")
    @Description("Create booking with missing required firstname field")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateBookingMissingFirstName(){
        Booking booking = new BookingRequestBuilder()
                .withFirstname(null)
                .build();

        Response response = given()
                .spec(requestSpec)
                .body(booking)
                .when()
                .post("/booking")
                .then()
                .extract().response();

        // Note: Restful Booker returns 500 instead of 400 for invalid requests
        // This is a known limitation of this practice API
        BookingResponseValidator.validateErrorResponse(response);
        System.out.println("TC03 passed");
    }

    // TC04 - Empty Request Body
    @Test
    @Story("Create Booking")
    @Description("Create booking with empty request body")
    @Severity(SeverityLevel.MINOR)
    public void testEmptyRequestBody() {
        Response response = given()
                .spec(requestSpec)
                .body("{}")
                .when()
                .post("/booking")
                .then()
                .extract().response();

        BookingResponseValidator.validateErrorResponse(response);
        System.out.println("TC04 Status: " + response.getStatusCode());
    }

    // ==================== GET TESTS ====================

    // TC05 - Get valid booking by id
    @Test
    @Story("Create Booking")
    @Description("Create booking with empty request body")
    @Severity(SeverityLevel.MINOR)
    public void testBookingByID(){

        int freshId = createFreshBooking();

        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/booking/"+freshId)
                .then()
                .extract().response();

        BookingResponseValidator.validateBookingDetails(response,"John","Smith");
        System.out.println("GET TC05 passed");
    }

    //TC06 - get non existent booking
    @Test
    @Story("Get Booking")
    @Description("Get booking with non existent ID returns 404")
    @Severity(SeverityLevel.NORMAL)
    public void testGetNonExistentBooking(){
        Response response = given()
                .spec(requestSpec)
                .when()
                .get("/booking/99999")
                .then()
                .extract().response();

        BookingResponseValidator.validateNotFound(response);
        System.out.println("GET TC06 passed, 404 not found");
    }

    // ==================== PUT TESTS ====================

    //TC07 - PUT update booking succesfully
    @Test
    @Story("Update Booking")
    @Description("Update booking successfully with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void testUpdateBooking(){
        int freshId = createFreshBooking();

        Booking booking = new BookingRequestBuilder()
                .withFirstname("Jane")
                .withLastname("Doe")
                .withTotalprice(200)
                .withAdditionalneeds("lunch")
                .build();

        Response response = given()
                .spec(requestSpec)
                .auth().preemptive().basic(Constants.ADMIN_USERNAME, Constants.ADMIN_PASSWORD)
                .body(booking)
                .when()
                .put("/booking/"+freshId)
                .then()
                .extract().response();

        // Note: Restful Booker sometimes returns 418 for PUT
        // This is a known limitation of this practice API
        BookingResponseValidator.validateBookingDetails(response, "Jane", "Doe");
        System.out.println("TC07 PUT status");
    }

    // ==================== DELETE TESTS ====================

    //TC08 Delete: delete booking successfully
    @Test
    @Story("Delete Booking")
    @Description("Delete booking successfully with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteBooking(){
        int freshId = createFreshBooking();

        Response response = given()
                .spec(requestSpec)
                .auth().preemptive().basic(Constants.ADMIN_USERNAME,Constants.ADMIN_PASSWORD)
                .when()
                .delete("/booking/"+freshId)
                .then()
                .extract().response();

        BookingResponseValidator.validateDeleted(response);
        System.out.println("DELETE TC08 Passed! Deleted booking: " + freshId);

    }

}
