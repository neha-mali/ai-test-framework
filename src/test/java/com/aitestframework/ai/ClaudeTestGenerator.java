package com.aitestframework.ai;

import com.aitestframework.utils.ConfigReader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;

// ClaudeTestGenerator — calls Claude AI to generate test cases
public class ClaudeTestGenerator {

    // Anthropic server where HTTP request goes
    private static final String CLAUDE_API_URL =
            "https://api.anthropic.com/v1/messages";

    private static final String MODEL = "claude-sonnet-4-6";

    // used to build JSON requests and parse JSON response
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateTestCases(String apiDescription) {
        try {
            // create empty JSON object {}
            ObjectNode requestBody = objectMapper.createObjectNode();

            // {"model" : "claude-sonnet"}
            requestBody.put("model", MODEL);

            // response limit 1000 tokesn = 750 words
            requestBody.put("max_tokens", 1000);

            // Build messages array

            // empty JSON array [], claud api requires message in array format
            ArrayNode messages = objectMapper.createArrayNode();

            // create one message object {}
            ObjectNode message = objectMapper.createObjectNode();

            // this will tell Claude this message is from user and claud API always needs a role - user or assistant
            message.put("role", "user");


            message.put("content",
                    "You are an expert SDET. Generate test cases for this API:\n\n"
                            + apiDescription
                            + "\n\nFor each test case provide:\n"
                            + "1. Test case name\n"
                            + "2. Description\n"
                            + "3. Input data\n"
                            + "4. Expected result\n"
                            + "Keep it concise and practical.");

            // add message to array
            messages.add(message);

            // add array to requestBody
            requestBody.set("messages", messages);

            // json will look like this
            /*
            {
                "model": "claude-sonnet-4-6",
                    "max_tokens": 1000,
                    "messages": [{
                "role": "user",
                        "content": "You are an expert SDET..."
            }]
            }
             */

            // Make HTTP call to Claude API
            CloseableHttpClient httpClient = HttpClients.createDefault();

            // creates POST request pointing to claude url
            HttpPost request = new HttpPost(CLAUDE_API_URL);

            // Set headers
            // tells anthropic we are sending json and this is required ow request reject
            request.setHeader("Content-Type", "application/json");

            // with this key anthropic knows its you
            request.setHeader("x-api-key", ConfigReader.getClaudeApiKey());
            request.setHeader("anthropic-version", "2023-06-01");

            // Set body
            // attach JSON request body to HTTP request
            // requestbody is java object and it converts to string
            // now this is string
            /*
            {
                "model": "claude-sonnet-4-6",
                    "max_tokens": 1000,
                    "messages": [{
                "role": "user",
                        "content": "You are an expert SDET..."
            }]
            }
             */
            // new StringEntity will wrap JSON string into HTTP entity
            // setEntity attach envelope to HTTP request
            // claude api will read this request
            request.setEntity(new StringEntity(
                    objectMapper.writeValueAsString(requestBody)));


            // Execute and get response
            // response
            /*
            {
              "content": [{
                "type": "text",
                "text": "Test Case 1: Valid booking..."
              }]
             */
            String responseBody = httpClient.execute(request, response ->
                    new String(response.getEntity().getContent().readAllBytes()));

            // Parse response to Java Object turning flat text into structured tree
            JsonNode jsonResponse = objectMapper.readTree(responseBody);

            // return claude text as text
            //System.out.println("Raw response: " + responseBody);

            if (jsonResponse.has("content")) {
                return jsonResponse
                        .get("content")
                        .get(0)
                        .get("text")
                        .asText();
            } else {
                return "Error from Claude API: " + responseBody;
            }

        } catch (Exception e) {
            return "Error generating test cases: " + e.getMessage();
        }
    }

    /*
    generateTestCases("POST /booking...")
        ↓
    Build JSON request body
            ↓
    Attach API key in headers
            ↓
    Send POST to api.anthropic.com
            ↓
    Claude processes prompt
            ↓
    Returns JSON with test cases
            ↓
    Parse JSON → extract text
            ↓
    Return test cases as String
     */
}