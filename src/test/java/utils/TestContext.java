package utils;

import driverfactory.DriverFactory;
import org.openqa.selenium.WebDriver;

public class TestContext {
    private static final ThreadLocal<TestContext> CONTEXT = new ThreadLocal<>();

    public TestContext() {
        CONTEXT.set(this);
    }

    public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    public static TestContext getContext() {
        return CONTEXT.get();
    }

    public void tearDown() {
        CONTEXT.remove();
    }
}