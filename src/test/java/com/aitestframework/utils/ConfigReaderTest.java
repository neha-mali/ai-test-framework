package com.aitestframework.utils;

public class ConfigReaderTest {
    public static void main(String[] args) {
        String apiKey = ConfigReader.getClaudeApiKey();

        if (apiKey != null && !apiKey.isEmpty()) {
            System.out.println("✅ API Key loaded successfully!");
            System.out.println("Key starts with: " + apiKey.substring(0, 10) + "...");
        } else {
            System.out.println("❌ API Key not found!");
        }
    }
}
