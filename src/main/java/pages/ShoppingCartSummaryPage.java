package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.SeleniumHelper;
import java.time.Duration;

import static java.lang.System.getProperty;

/**
 * @author hemanth.shivashankrappa on 16/06/2018
 * @project interview-test
 */
public class ShoppingCartSummaryPage extends BasePage {

    private static final By SIGN_OUT_LINK = By.xpath("//a[@title='Log me out']");
    private final SeleniumHelper helper;
    private final WebDriverWait wait;

    public ShoppingCartSummaryPage(WebDriver driver) {
        super(driver);
        this.helper = new SeleniumHelper(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isItemInCart() {
        String product = "//tr[starts-with(@id,'product_{}')]";
        System.out.println(getProperty("productId"));
        return helper.waitForElementToBeVisible(By.xpath(product.replace("{}", getProperty("productId"))));
    }

    public boolean isCartEmpty() {
        try {
            WebElement cart = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("_desktop_cart")));
            return cart.isEnabled() && cart.isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public ShoppingCartSummaryPage logOut() {
        wait.until(ExpectedConditions.elementToBeClickable(SIGN_OUT_LINK)).click();
        return this;
    }

    public ShoppingCartSummaryPage deleteProductFromCart(String productId) {
        String deleteProduct = ".remove-from-cart[data-id-product='{}']";
        By deleteLocator = By.cssSelector(deleteProduct.replace("{}", productId));
        wait.until(ExpectedConditions.elementToBeClickable(deleteLocator)).click();
        helper.waitForElementNotVisible(deleteLocator);
        return this;
    }

    public boolean isItemInCart(String productId) {
        By productLocator = By.cssSelector(".cart-items [data-product-id='{}']".replace("{}", productId));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(productLocator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
