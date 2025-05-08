package utils;

import driverfactory.DriverFactory;
import org.openqa.selenium.WebDriver;

/**
 * Manages test context using thread-local storage to ensure thread safety in parallel test execution.
 * Provides access to WebDriver and other test-specific resources within the current test thread.
 */
public class TestContext {
    private static final ThreadLocal<TestContext> CONTEXT = new ThreadLocal<>();

    /**
     * Creates a new TestContext and binds it to the current thread.
     */
    public TestContext() {
        CONTEXT.set(this);
    }

    /**
     * Gets the WebDriver instance for the current test thread.
     * 
     * @return The WebDriver instance from DriverFactory
     */
    public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    /**
     * Retrieves the TestContext instance for the current thread.
     * 
     * @return The TestContext bound to the current thread
     */
    public static TestContext getContext() {
        return CONTEXT.get();
    }

    /**
     * Cleans up resources by removing the thread-local reference.
     * Should be called at the end of each test scenario.
     */
    public void tearDown() {
        CONTEXT.remove();
    }
}
