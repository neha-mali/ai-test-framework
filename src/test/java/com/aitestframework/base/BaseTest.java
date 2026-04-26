package com.aitestframework.base;
import com.aitestframework.utils.Constants;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;


// In every file we have to initialize the base uri so instead of that we make a base class and add uri and then extends
// RequestSpecBuilder tool to build a reusable request specification, like a template for all HTTP req

public class BaseTest {


    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setupSpec() {
        // base uri set to globally for RestAssured, every request will hit this url

        requestSpec = new RequestSpecBuilder() // this create an empty object and fill in
                .setBaseUri(Constants.BASE_URL)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build(); // create a object

        System.out.println("Base setup complete - REST framework initialized");
    }



}

// useful as if something changes we just have to make change at one place


/*
Before every test class runs
        ↓
BaseTest sets up base URL
        ↓
Creates reusable requestSpec
        ↓
Every test class just extends BaseTest
        ↓
No need to repeat setup anywhere
 */
