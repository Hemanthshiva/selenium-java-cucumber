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
 * Page object for the Shopping Cart Summary page.
 * Provides actions and assertions related to cart contents and user authentication status.
 */
public class ShoppingCartSummaryPage extends BasePage {

    private static final By SIGN_OUT_LINK = By.xpath("//a[@title='Log me out']");
    private final SeleniumHelper helper;
    private final WebDriverWait wait;

    /**
     * Constructor initializes helper and wait utilities.
     * @param driver The WebDriver instance for interacting with cart page.
     */
    public ShoppingCartSummaryPage(WebDriver driver) {
        super(driver);
        this.helper = new SeleniumHelper(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    /**
     * Checks whether the previously selected product is present in the cart.
     * Uses the product ID from system properties.
     * @return true if the item is present, false otherwise.
     */
    public boolean isItemInCart() {
        String productXpath = "//tr[starts-with(@id,'product_{}')]";
        String productId = getProperty("productId");
        return helper.waitForElementToBeVisible(By.xpath(productXpath.replace("{}", productId)));
    }

    /**
     * Determines if the cart UI is currently displayed and enabled (cart considered empty).
     * @return true if cart is present and empty, false otherwise.
     */
    public boolean isCartEmpty() {
        try {
            WebElement cart = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("_desktop_cart")));
            return cart.isEnabled() && cart.isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Logs out the current user from the shopping cart summary page.
     * @return This ShoppingCartSummaryPage instance.
     */
    public ShoppingCartSummaryPage logOut() {
        wait.until(ExpectedConditions.elementToBeClickable(SIGN_OUT_LINK)).click();
        return this;
    }

    /**
     * Removes a product from the cart by its productId.
     * Waits for the cart to be updated (element no longer visible).
     * @param productId The product ID to delete.
     * @return This ShoppingCartSummaryPage instance.
     */
    public ShoppingCartSummaryPage deleteProductFromCart(String productId) {
        String deleteSelector = ".remove-from-cart[data-id-product='{}']".replace("{}", productId);
        By deleteLocator = By.cssSelector(deleteSelector);
        wait.until(ExpectedConditions.elementToBeClickable(deleteLocator)).click();
        helper.waitForElementNotVisible(deleteLocator);
        return this;
    }

    /**
     * Checks if a product with a specific product ID exists in the cart.
     * @param productId The product ID to check for.
     * @return true if the product is in the cart, false otherwise.
     */
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
