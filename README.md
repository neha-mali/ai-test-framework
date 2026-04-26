# AI-Powered API Test Framework

An AI-augmented test automation framework that uses Claude AI for intelligent test case generation and RestAssured for API test execution, with CI/CD integration via GitHub Actions.

## What This Project Does

- Uses Claude AI to automatically generate test cases from API descriptions
- Automates API tests using RestAssured and TestNG
- Generates Allure HTML reports with detailed test analytics
- Runs tests automatically on every push via GitHub Actions CI/CD
- Implements Builder Pattern for clean test data creation
- Centralized response validation for maintainable assertions
- Parallel test execution for faster test runs
- Retry mechanism for handling flaky tests

## Tech Stack

- Java — Core programming language
- RestAssured — API test automation
- TestNG — Test runner and assertions
- Claude AI API — AI test case generation
- Maven — Build and dependency management
- Allure — Interactive HTML test reporting
- GitHub Actions — CI/CD pipeline


## Setup

### 1. Clone the repository
git clone https://github.com/neha-mali/ai-test-framework.git
cd ai-test-framework

### 2. Add Claude API key
Create src/test/resources/config.properties:
claude.api.key=your_api_key_here
env=dev
dev.base.url=https://restful-booker.herokuapp.com
dev.admin.username=admin
dev.admin.password=password123

### 3. Install Allure CLI (Mac)
arch -arm64 brew install allure

## How to Run Tests

### Run all tests
mvn clean test

### Run specific environment
mvn clean test -Denv=staging

### Run specific test
mvn clean test -Dtest=BookingApiTest

## Generate Allure Report

### Step 1 - Run tests
mvn clean test

### Step 2 - Open Allure report
allure serve target/allure-results

Report opens automatically in browser showing:
- Test pass/fail summary
- Trend graphs
- Detailed test steps
- Request/response details
- Severity breakdown

## CI/CD Pipeline

Every push to main branch automatically:
1. Spins up Ubuntu server on GitHub
2. Installs Java 11
3. Runs all test cases
4. Uploads test results as artifact


