package driverfactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for managing WebDriver instances for various browsers.
 * Supports thread-safe driver usage and automatic driver management.
 */
public final class DriverFactory {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final List<WebDriver> allDrivers = new ArrayList<>();
    private static final DriverFactory instance = new DriverFactory();

    // Browser types
    private static final String BROWSER_CHROME = "chrome";
    private static final String BROWSER_FIREFOX = "firefox";
    private static final String BROWSER_EDGE = "edge";
    private static final String BROWSER_SAFARI = "safari";

    // Configuration constants
    private static final int IMPLICIT_WAIT_TIMEOUT = 20;
    private static final int PAGE_LOAD_TIMEOUT = 30;
    private static final int SCRIPT_TIMEOUT = 20;
    private static final String HEADLESS_PROPERTY = "headless";
    private static final String BROWSER_PROPERTY = "browser";

    // Private constructor to ensure singleton usage
    private DriverFactory() {}

    /**
     * Get singleton instance for factory usage.
     * @return DriverFactory instance
     */
    public static DriverFactory getInstance() {
        return instance;
    }

    /**
     * Creates a new WebDriver instance as per system properties.
     * Supported browsers: chrome, firefox, edge, safari.
     * Stores instance in thread-local and all-drivers list for cleanup.
     *
     * System properties:
     * -Dbrowser=[chrome|firefox|edge|safari]
     * -Dheadless=true|false
     *
     * @return newly created WebDriver
     * @throws IllegalStateException when creation fails or unsupported browser requested
     */
    public WebDriver create() {
        String browser = System.getProperty(BROWSER_PROPERTY, BROWSER_CHROME).toLowerCase().trim();
        boolean headless = Boolean.parseBoolean(System.getProperty(HEADLESS_PROPERTY, "false"));

        try {
            WebDriver driver = createDriver(browser, headless);
            if (driver == null) {
                throw new IllegalStateException("Failed to create WebDriver instance for browser: " + browser);
            }
            configureDriver(driver);
            driverThreadLocal.set(driver);
            synchronized (allDrivers) {
                allDrivers.add(driver);
            }
            return driver;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize WebDriver: " + e.getMessage(), e);
        }
    }

    /**
     * Gets the WebDriver instance for the current thread.
     * @return WebDriver for current thread, or null if not initialized
     */
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    /**
     * Quits and cleans up all created WebDriver instances.
     * Recommended to call after test suite execution completes.
     */
    public static void quitAllDrivers() {
        synchronized (allDrivers) {
            allDrivers.forEach(driver -> {
                try {
                    if (driver != null) {
                        driver.quit();
                    }
                } catch (Exception e) {
                    System.err.println("Error while quitting WebDriver: " + e.getMessage());
                }
            });
            allDrivers.clear();
        }
        driverThreadLocal.remove();
    }

    /**
     * Quits and cleans up the WebDriver instance for the current thread.
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
                synchronized (allDrivers) {
                    allDrivers.remove(driver);
                }
            } catch (Exception e) {
                System.err.println("Error while quitting WebDriver: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }

    /**
     * Internal logic for constructing correct WebDriver for given browser/headless configuration.
     * @param browser browser type as string
     * @param headless whether to enable headless mode
     * @return new WebDriver for selected browser
     */
    private WebDriver createDriver(String browser, boolean headless) {
        switch (browser) {
            case BROWSER_CHROME:
                return createChromeDriver(headless);
            case BROWSER_FIREFOX:
                return createFirefoxDriver(headless);
            case BROWSER_EDGE:
                return createEdgeDriver(headless);
            case BROWSER_SAFARI:
                return createSafariDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    private WebDriver createChromeDriver(boolean headless) {
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if (headless) {
                options.addArguments("--headless=new");
            }
            options.addArguments("--start-maximized");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-infobars");
            options.setAcceptInsecureCerts(true);
            return new ChromeDriver(options);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize Chrome driver: " + e.getMessage(), e);
        }
    }

    private WebDriver createFirefoxDriver(boolean headless) {
        try {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            if (headless) {
                options.addArguments("--headless");
            }
            // Note: Firefox does not support --start-maximized; consider using set window size if desired.
            return new FirefoxDriver(options);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize Firefox driver: " + e.getMessage(), e);
        }
    }

    private WebDriver createEdgeDriver(boolean headless) {
        try {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            if (headless) {
                options.addArguments("--headless");
            }
            options.addArguments("--start-maximized");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            return new EdgeDriver(options);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize Edge driver: " + e.getMessage(), e);
        }
    }

    private WebDriver createSafariDriver() {
        try {
            SafariOptions options = new SafariOptions();
            return new SafariDriver(options);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize Safari driver: " + e.getMessage(), e);
        }
    }

    /**
     * Configures timeouts and maximizes browser window for a given driver.
     * @param driver WebDriver to configure
     */
    private void configureDriver(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_TIMEOUT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(SCRIPT_TIMEOUT));
        driver.manage().window().maximize();
    }
}
