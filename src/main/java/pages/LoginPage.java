package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author hemanth.shivashankrappa on 16/06/2018
 * @project interview-test
 */
public class LoginPage extends BasePage {

	private static final By SIGN_IN_LINK = By.linkText("Sign in");
	private static final By EMAIL_ADDRESS = By.id("email");
	private static final By PASSWORD = By.id("passwd");
	private static final By LOGIN_BUTTON = By.id("SubmitLogin");

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public HomePage login(String username, String password) {

		driver.findElement(SIGN_IN_LINK).click();
		driver.findElement(EMAIL_ADDRESS).sendKeys(username);
		driver.findElement(PASSWORD).sendKeys(password);
		driver.findElement(LOGIN_BUTTON).click();
		return new HomePage(driver);

	}

}
