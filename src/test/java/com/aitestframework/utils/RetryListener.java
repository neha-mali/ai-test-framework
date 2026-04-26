package com.aitestframework.utils;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryListener implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}

// which test to apply without writing manually, every test apply

/*
testng.xml registers RetryListener
        ↓
TestNG finds @Test on testCreateBooking
        ↓
Calls RetryListener.transform()
        ↓
RetryListener attaches RetryAnalyzer to @Test
        ↓
Test runs and fails
        ↓
TestNG sees RetryAnalyzer on @Test
        ↓
Calls RetryAnalyzer.retry()
        ↓
RetryAnalyzer says "retry!" or "stop!"
 */