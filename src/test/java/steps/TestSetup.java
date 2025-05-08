package steps;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import driverfactory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;

/**
 * Handles test scenario setup and teardown.
 * Loads configuration, initializes WebDriver, and manages browser session per scenario.
 */
public class TestSetup {
    private WebDriver driver;
    private static final Properties props = new Properties();

    /**
     * Sets up the test scenario: loads properties and opens the application URL.
     */
    @Before
    public void setUp() {
        try {
            loadProperties();
            String url = props.getProperty("URL");
            if (url == null || url.trim().isEmpty()) {
                throw new RuntimeException("URL not found in config.properties");
            }
            
            driver = DriverFactory.getInstance().create();
            driver.get(url);
            
        } catch (Exception e) {
            throw new RuntimeException("Test setup failed: " + e.getMessage(), e);
        }
    }

    /**
     * Loads properties from test resources configuration file.
     */
    private void loadProperties() {
        String configPath = "src/test/resources/config.properties";
        try (FileInputStream input = new FileInputStream(configPath)) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage(), e);
        }
    }

    /**
     * Cleans up after each scenario by quitting the browser.
     */
    @After
    public void tearDown() {
        if (driver != null) {
            DriverFactory.quitDriver();
            driver = null;
        }
    }

    /**
     * Returns the WebDriver used for the test.
     * @return The active WebDriver instance.
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Returns property value for the given key in the loaded configuration.
     * @param key property name
     * @return property value, or null if not present
     */
    public String getProperty(String key) {
        return props.getProperty(key);
    }
}
