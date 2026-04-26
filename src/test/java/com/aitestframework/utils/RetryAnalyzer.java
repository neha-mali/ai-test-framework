package com.aitestframework.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY) {
            retryCount++;
            System.out.println("Retrying test: "
                    + result.getName()
                    + " attempt "
                    + retryCount
                    + " of "
                    + MAX_RETRY);
            return true;
        }
        return false;
    }
}


/*
What is ITestResult?
When a test finishes — pass or fail — TestNG creates an ITestResult object containing:
ITestResult
├── test name
├── pass/fail status
├── error message
├── start time
├── end time
└── test class name

result.getName()         // "testCreateBookingWithAllFields"
result.getStatus()       // 1=pass, 2=fail, 3=skip
result.getThrowable()    // the actual error/exception
result.getStartMillis()  // when test started
result.getEndMillis()    // when test ended
result.getTestClass()    // which class the test is in


Test fails
        ↓
TestNG creates ITestResult with failure details
        ↓
TestNG calls RetryAnalyzer.retry(result)
        ↓
RetryAnalyzer checks retryCount < MAX_RETRY
        ↓
If yes → return true → TestNG reruns the test
        ↓
If no → return false → TestNG marks test as failed

// First attempt
testGetBookingById FAILED — Connection timeout

// RetryAnalyzer kicks in
Retrying test: testGetBookingById attempt 1 of 2

// Second attempt
testGetBookingById FAILED — Connection timeout

// RetryAnalyzer kicks in again
Retrying test: testGetBookingById attempt 2 of 2

// Third attempt
testGetBookingById PASSED ✅ OR FAILED ❌

2 - less falky
3 - moderatley flaky


Without RetryAnalyzer:
Test fails once → marked as FAILED → you investigate → it was just network!
Wasted time debugging false failures!

With RetryAnalyzer:
Test fails once → automatically retried → passes on second attempt
No false failures — only real bugs reported!

 */


