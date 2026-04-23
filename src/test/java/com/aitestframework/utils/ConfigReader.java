package com.aitestframework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();

    static {
        try {
            InputStream input = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");
            if (input == null) {
                throw new RuntimeException("config.properties file not found");
            }
//            reads file and loads all key-value pairs into memory
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getClaudeApiKey() {
        return properties.getProperty("claude.api.key");
    }
}

//this is an utility class it is just called by other class we can't run it

/*
        Program starts
        ↓
        static block runs automatically
        ↓
        finds config.properties in resources folder
        ↓
        loads claude.api.key into memory
        ↓
        ClaudeTestGenerator calls getClaudeApiKey()
        ↓
        returns your API key

*/