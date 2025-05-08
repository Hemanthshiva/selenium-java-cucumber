package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for page objects.
 * Provides shared WebDriver reference and logging functionality.
 */
public class BasePage {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected WebDriver driver;

    /**
     * Constructor initializes WebDriver and PageFactory elements.
     * @param driver WebDriver instance to be used by the page object.
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    /**
     * Logs informational messages relevant to page actions.
     * @param message the message to log
     */
    protected void log(String message) {
        logger.info(message);
    }
}
