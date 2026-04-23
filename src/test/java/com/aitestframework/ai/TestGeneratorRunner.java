package com.aitestframework.ai;

public class TestGeneratorRunner {

    public static void main(String [] args){

        ClaudeTestGenerator generator = new ClaudeTestGenerator();

        String apiDescription =
                "POST /booking - Creates a new hotel booking. " +
                        "Request body fields: " +
                        "firstname (String, required), " +
                        "lastname (String, required), " +
                        "totalprice (Integer, required), " +
                        "depositpaid (Boolean, required), " +
                        "checkin (String, required, format: YYYY-MM-DD), " +
                        "checkout (String, required, format: YYYY-MM-DD), " +
                        "additionalneeds (String, optional). " +
                        "Returns 200 with booking ID on success.";

        System.out.println("Generating test cases...\n");
        String testCases = generator.generateTestCases(apiDescription);
        System.out.println(testCases);
    }
}


