package util;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author hemanth.shivashankrappa on 16/06/2018
 * @project interview-testankrappa on 16/06/2018
 * @project interview-test
 */
public class SeleniumHelper {
    private static final int DEFAULT_TIMEOUT = 5;
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    public SeleniumHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        this.actions = new Actions(driver);
    }

    /**
     * Wait for element to be visible with a timeout of 5 seconds
     * 
     * @param by The locator to find the element
     * @return true if element becomes visible, false if timeout occurs
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
     * Wait for element to disappear with a timeout of 5 seconds
     * 
     * @param by The locator to find the element
     * @return true if element becomes invisible, false if timeout occurs
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
     * Hover over the element using Selenium Actions class
     * Falls back to JavaScript if Actions hover fails
     * 
     * @param element The WebElement to hover over
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
     * Wait for element to be clickable with a timeout of 5 seconds
     * 
     * @param by The locator to find the element
     * @return true if element becomes clickable, false if timeout occurs
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
     * Wait for element to be present in DOM with a timeout of 5 seconds
     * 
     * @param by The locator to find the element
     * @return true if element is present, false if timeout occurs
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
     * Scroll to the element using JavaScript
     * 
     * @param element The WebElement to scroll to
     */

    public void scrollToElement(WebElement element) {
        String javaScript = "arguments[0].scrollIntoView(true);";
        ((JavascriptExecutor) driver).executeScript(javaScript, element);
    }

    public void scrollToElement(By by) {
        WebElement element = driver.findElement(by);
        scrollToElement(element);
    }
}
