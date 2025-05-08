package util;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Utility class providing common Selenium interaction and wait helpers.
 */
public class SeleniumHelper {
    private static final int DEFAULT_TIMEOUT = 5;
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    /**
     * Constructs the SeleniumHelper with a WebDriver.
     * @param driver WebDriver instance to use for actions and waits.
     */
    public SeleniumHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        this.actions = new Actions(driver);
    }

    /**
     * Waits until the element specified by the locator is visible.
     * 
     * @param by Locator of the element.
     * @return true if element becomes visible, false if timeout occurs.
     */
    public boolean waitForElementToBeVisible(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (TimeoutException te) {
            return false;
        }
    }

    /**
     * Waits until the element specified by the locator is no longer visible.
     * 
     * @param by Locator of the element.
     * @return true if element becomes invisible, false if timeout occurs.
     */
    public boolean waitForElementNotVisible(By by) {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            return true;
        } catch (TimeoutException te) {
            return false;
        }
    }

    /**
     * Performs a hover action over the given element using Actions class.
     * Falls back to JavaScript dispatch if Actions fails.
     * 
     * @param element The element to hover over.
     */
    public void hoverOverElement(WebElement element) {
        try {
            // First try using Actions class (preferred method)
            actions.moveToElement(element).perform();
        } catch (MoveTargetOutOfBoundsException e) {
            // Fallback to JavaScript if Actions fails
            String javaScript = "var evObj = document.createEvent('MouseEvents');" +
                    "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
                    +
                    "arguments[0].dispatchEvent(evObj);";
            ((JavascriptExecutor) driver).executeScript(javaScript, element);
        }
    }

    /**
     * Waits until the element specified by the locator is clickable.
     * 
     * @param by Locator of the element.
     * @return true if element becomes clickable, false if timeout occurs.
     */
    public boolean waitForElementToBeClickable(By by) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by));
            return true;
        } catch (TimeoutException te) {
            return false;
        }
    }

    /**
     * Waits until the element specified by the locator is present in the DOM.
     * 
     * @param by Locator of the element.
     * @return true if the element is present, false if timeout occurs.
     */
    public boolean waitForElementPresence(By by) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        } catch (TimeoutException te) {
            return false;
        }
    }

    /**
     * Scrolls to the given web element using JavaScript.
     * 
     * @param element The element to scroll to.
     */

    public void scrollToElement(WebElement element) {
        String javaScript = "arguments[0].scrollIntoView(true);";
        ((JavascriptExecutor) driver).executeScript(javaScript, element);
    }

    /**
     * Scrolls to the element located by the given locator using JavaScript.
     * 
     * @param by Locator for the element to scroll to.
     */
    public void scrollToElement(By by) {
        WebElement element = driver.findElement(by);
        scrollToElement(element);
    }
}
