# AI-Powered API Test Framework

An AI-augmented test automation framework that uses Claude AI for intelligent test case generation and RestAssured for API test execution, with CI/CD integration via GitHub Actions.

## 🚀 What This Project Does

- Uses **Claude AI** to automatically generate test cases from API descriptions
- Automates API tests using **RestAssured** and **TestNG**
- Generates **HTML test reports** using Maven Surefire
- Runs tests automatically on every push via **GitHub Actions CI/CD**

## 🏗️ Architecture

```
You describe API → Claude AI generates test cases → RestAssured automates them → GitHub Actions runs on every push
```

## 🛠️ Tech Stack

| Tool | Purpose |
|---|---|
| Java 11 | Core programming language |
| RestAssured | API test automation |
| TestNG | Test runner and assertions |
| Claude AI API | AI test case generation |
| Maven | Build and dependency management |
| GitHub Actions | CI/CD pipeline |
| Surefire Report | HTML test reporting |

## 📁 Project Structure

```
ai-test-framework/
├── src/test/java/com/aitestframework/
│   ├── ai/
│   │   ├── ClaudeTestGenerator.java    # Calls Claude AI to generate test cases
│   │   └── TestGeneratorRunner.java    # Runner to test AI generation
│   ├── api/
│   │   ├── BookingApiTest.java         # RestAssured API tests
│   │   ├── Booking.java                # POJO for request body
│   │   └── BookingDates.java           # POJO for nested booking dates
│   └── utils/
│       └── ConfigReader.java           # Reads API key from config
├── .github/workflows/
│   └── ci.yml                          # GitHub Actions CI/CD pipeline
└── pom.xml                             # Maven dependencies
```

## 🤖 How AI Test Generation Works

1. Describe an API endpoint in plain English
2. `ClaudeTestGenerator` sends description to Claude AI
3. Claude generates comprehensive test cases including edge cases
4. Test cases are automated using RestAssured

```java
ClaudeTestGenerator generator = new ClaudeTestGenerator();
String testCases = generator.generateTestCases(
    "POST /booking - Creates a hotel booking with firstname, lastname, totalprice..."
);
```

## ✅ Test Cases Covered

| Test Case | Description | Expected |
|---|---|---|
| TC01 | Create booking with all fields | 200 OK |
| TC02 | Create booking without optional field | 200 OK |
| TC03 | Missing required field firstname | 400/500 |
| TC04 | Empty request body | 400/500 |

## 🔧 How to Run Locally

### Prerequisites
- Java 11+
- Maven 3.x
- Claude API key from console.anthropic.com

### Setup
1. Clone the repository:
```bash
git clone https://github.com/neha-mali/ai-test-framework.git
```

2. Add your Claude API key:
```bash
# Create src/test/resources/config.properties
claude.api.key=your_api_key_here
```

3. Run tests:
```bash
mvn test
```

4. Generate HTML report:
```bash
mvn test surefire-report:report
open target/site/surefire-report.html
```

## 📊 CI/CD Pipeline

Every push to `main` branch automatically:
1. Spins up Ubuntu server on GitHub
2. Installs Java 11
3. Runs all test cases
4. Generates HTML test report
5. Uploads report as artifact

## 🎯 Key Highlights

- **AI-assisted testing** — Claude generates test cases from API specs reducing manual effort
- **Real API testing** — Tests run against live Restful Booker API
- **Automated pipeline** — Zero manual intervention needed after push
- **100% pass rate** — All 4 test cases passing