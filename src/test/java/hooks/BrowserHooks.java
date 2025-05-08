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
 * Cucumber hooks for browser lifecycle management.
 * Handles global browser startup/teardown, per-scenario initialization, and failure screenshot capture.
 */
public class BrowserHooks {
    private static final Properties props = new Properties();
    private static WebDriver sharedDriver;
    private TestContext testContext;

    /**
     * Loads external configuration before any tests are run.
     */
    @BeforeAll
    public static void beforeAll() {
        loadProperties();
    }

    /**
     * Sets up a new browser session and navigates to the initial URL before each scenario.
     */
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
            testContext = new TestContext();
        } catch (Exception e) {
            throw new RuntimeException("Test context initialization failed: " + e.getMessage(), e);
        }
    }

    /**
     * Loads configuration properties from file.
     */
    private static void loadProperties() {
        String configPath = System.getProperty("user.dir") + "/src/test/resources/config.properties";
        try (FileInputStream input = new FileInputStream(configPath)) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage(), e);
        }
    }

    /**
     * Captures screenshot on failure and performs per-scenario teardown.
     */
    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        if (sharedDriver != null && scenario.isFailed()) {
            captureScreenshot(scenario);
        }
        if (testContext != null) {
            testContext.tearDown();
        }
    }

    /**
     * Takes and attaches a failure screenshot to the scenario.
     */
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

    /**
     * Cleans up after test suite: closes browser and releases driver resources.
     */
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

    /**
     * Gets loaded configuration property by key.
     * @param key the property name in config
     * @return the value or null
     */
    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}
