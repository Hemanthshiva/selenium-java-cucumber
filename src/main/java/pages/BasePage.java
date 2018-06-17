package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * @author hemanth.shivashankrappa on 16/06/2018
 * @project interview-test
 */
class BasePage {

	WebDriver driver;

	BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

}
