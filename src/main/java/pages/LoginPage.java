package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

/**
 * @author hemanth.shivashankrappa on 16/06/2018
 * @project interview-test
 */
public class LoginPage extends BasePage {

    private static final By SIGN_IN_LINK = By.linkText("Sign in");
    private static final By EMAIL_ADDRESS = By.id("field-email");
    private static final By PASSWORD = By.id("field-password");
    private static final By LOGIN_BUTTON = By.id("submit-login");
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public HomePage login(String username, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(SIGN_IN_LINK)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(EMAIL_ADDRESS)).sendKeys(username);
        wait.until(ExpectedConditions.presenceOfElementLocated(PASSWORD)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON)).click();
        return new HomePage(driver);
    }
}
