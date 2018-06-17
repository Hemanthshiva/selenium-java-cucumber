package util;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author hemanth.shivashankrappa on 16/06/2018
 * @project interview-test
 */
public class SeleniumHelper {

	private int DEFAULT_TIMEOUT = 5;
	private WebDriver driver;

	public SeleniumHelper(WebDriver driver) {
		this.driver = driver;
	}

    /**
     * Wait for element to be visible with a timeout of 5 seconds
     */
	public boolean waitForElementToBeVisible(By by) {

		try {
			new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (TimeoutException te) {
			return false;
		}
		return true;
	}


    /**
     * Wait for element to disappear with a timeout of 5 seconds
     */
	public boolean waitForElementNotVisible(By by) {

		try {
			new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.invisibilityOfElementLocated(by));
		} catch (TimeoutException te) {
			return false;
		}
		return true;
	}


    /**
     * Hover over the element and execute the below javascript so that the element is clickable
     */
	public void hoverOverElement(WebElement element) {

		String javaScript = "var evObj = document.createEvent('MouseEvents');" +
				"evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
				"arguments[0].dispatchEvent(evObj);";
		((JavascriptExecutor) driver).executeScript(javaScript, element);

	}

}
