package hooks;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import driverfactory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import utils.TestContext;

/**
 * Cucumber hooks for browser management
 * Handles browser initialization, cleanup, and screenshot capture
 * Single source of truth for browser initialization
 */
public class BrowserHooks {
    private static final Properties props = new Properties();
    private static WebDriver sharedDriver;
    private TestContext testContext;

    @BeforeAll
    public static void beforeAll() {
        loadProperties();
    }

    @Before(order = 0)
    public void beforeScenario() {
        try {
            if (sharedDriver == null) {
                String url = props.getProperty("URL");
                if (url == null || url.trim().isEmpty()) {
                    throw new RuntimeException("URL not found in config.properties");
                }

                sharedDriver = DriverFactory.getInstance().create();
                sharedDriver.get(url);
            }

            // Initialize TestContext with shared driver
            testContext = new TestContext();

        } catch (Exception e) {
            throw new RuntimeException("Test context initialization failed: " + e.getMessage(), e);
        }
    }

    private static void loadProperties() {
        String configPath = System.getProperty("user.dir") + "/src/test/resources/config.properties";
        try (FileInputStream input = new FileInputStream(configPath)) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage(), e);
        }
    }

    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        if (sharedDriver != null && scenario.isFailed()) {
            captureScreenshot(scenario);
        }

        if (testContext != null) {
            testContext.tearDown();
        }
    }

    private void captureScreenshot(Scenario scenario) {
        try {
            if (sharedDriver instanceof TakesScreenshot) {
                byte[] screenshot = ((TakesScreenshot) sharedDriver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png",
                        String.format("Failure Screenshot - %s", scenario.getName()));
            }
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }

    @AfterAll
    public static void afterAll() {
        if (sharedDriver != null) {
            try {
                sharedDriver.quit();
            } finally {
                sharedDriver = null;
            }
        }
        DriverFactory.quitAllDrivers();
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}