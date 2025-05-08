package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * @author hemanth.shivashankrappa on 16/06/2018
 * @project interview-test
 */
class BasePage {

	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(BasePage.class);

	WebDriver driver;

	BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	protected void log(String message) {
		logger.info(message);
	}

}
