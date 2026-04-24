package com.aitestframework.ai;

public class TestGeneratorRunner {

    public static void main(String [] args) {

        ClaudeTestGenerator generator = new ClaudeTestGenerator();

        //generate for all endpoints
        generateForPost(generator);
        generateForGet(generator);
        generateForPut(generator);
        generateForDelete(generator);
    }

    // we will be using this method only inside this class so we made it private and we are making it static
    // because static method only calls static method ow we would have to create object of TestGeneratorRunner
    //what type of object java is receiving
    private static void generateForPost(ClaudeTestGenerator generator){
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

        System.out.println("Generating test cases for POST\n");
        String testCases = generator.generateTestCases(apiDescription);
        System.out.println(testCases);
    }

    //GET call
    private static void generateForGet(ClaudeTestGenerator generator) {
        String apiDescription =
                "GET /booking/{id} - Retrieves a specific hotel booking by ID"
                        + "path parameter : id (Integer,required)"
                        + "return 200 with booking details if found"
                        + "return 404 if booking not found";

        System.out.println("Generating test cases for GET\n");
        String testCases = generator.generateTestCases(apiDescription);
        System.out.println(testCases);
    }

    //PUT call
    private static void generateForPut(ClaudeTestGenerator generator){
        String apiDescription =
                "PUT /booking/{id} - updates an existing hotel booking"
                +"path parameter: id (Integer,required)"
                +"requires basic authentication: admin/password123"
                +"Body: same fields as above POST, all required"
                +"Returns 200 with updated booking"
                +"Returns 403 if not authenticated"
                +"Return 404 if booking if found";

        System.out.println("Put /booking/{id} test cases");
        System.out.println(generator.generateTestCases(apiDescription));
        System.out.println("\n");
    }

    // delete call
    private static void generateForDelete(ClaudeTestGenerator generator) {
        String apiDescription =
                "DELETE /booking/{id} - Deletes an existing hotel booking. " +
                        "Path parameter: id (Integer, required). " +
                        "Requires basic auth: admin/password123. " +
                        "Returns 201 on successful deletion. " +
                        "Returns 403 if not authenticated. " +
                        "Returns 404 if booking not found.";

        System.out.println("=== DELETE /booking/{id} Test Cases ===");
        System.out.println(generator.generateTestCases(apiDescription));
        System.out.println("\n");
    }


}


