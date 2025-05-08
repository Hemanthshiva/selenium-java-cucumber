package steps;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import driverfactory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class TestSetup {
    private WebDriver driver;
    private static final Properties props = new Properties();

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

    private void loadProperties() {
        String configPath = "src/test/resources/config.properties";
        try (FileInputStream input = new FileInputStream(configPath)) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage(), e);
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            DriverFactory.quitDriver();
            driver = null;
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }
}