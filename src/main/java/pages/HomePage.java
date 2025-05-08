package pages;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.SeleniumHelper;

/**
 * Page object representing the Home Page in the e-commerce application.
 * Contains methods for menu navigation, product interaction, sorting, and price handling.
 */
public class HomePage extends BasePage {

    private final SeleniumHelper helper;
    private final WebDriverWait wait;

    // Locators
    private static final By DROP_DOWN = By.cssSelector("[aria-label='Sort by selection']");
    private static final By PROCEED_TO_CHECKOUT_BUTTON = By.cssSelector(".cart-content a");
    private static final By PRICE_LIST = By.cssSelector("[aria-label='Price']");
    private static final By VIEW_MY_CART = By.xpath("//a[@title='View my shopping cart']");
    private static final By SELECTED_PRODUCT_LOCATOR = By.cssSelector(".cart-item input");

    /**
     * Constructor for HomePage.
     * @param driver The WebDriver instance to interact with the browser.
     */
    public HomePage(WebDriver driver) {
        super(driver);
        this.helper = new SeleniumHelper(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Clicks a menu item by visible text.
     * @param item The visible text of the menu item to click.
     * @return This HomePage instance, for method chaining.
     */
    public HomePage clickOnMenuItem(String item) {
        String menuXpath = "//*[@id='top-menu']/li[contains(., '{}')]";
        WebElement targetMenu = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath(menuXpath.replace("{}", item)))
        );
        helper.scrollToElement(targetMenu);
        targetMenu.click();
        return this;
    }

    /**
     * Sorts the products on the page by the provided criterion.
     * @param sortBy The visible text of the sort option.
     */
    public void sortSelection(String sortBy) {
        WebElement dropDown = wait.until(ExpectedConditions.presenceOfElementLocated(DROP_DOWN));
        helper.scrollToElement(dropDown);
        dropDown.click();
        WebElement sortOption = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(String.format("//a[contains(text(), '%s')]", sortBy))
                )
        );
        sortOption.click();
    }

    /**
     * Gets the maximum or minimum price available on the page.
     * @param range Accepts "max" or "min" (case-insensitive) to retrieve respective price.
     * @return The price as a string.
     */
    public String getPrice(String range) {
        List<Double> prices = priceList().stream()
                .map(s -> Double.parseDouble(s.replace("$", "").trim()))
                .collect(Collectors.toList());

        double price = 0;
        if (range.equalsIgnoreCase("max")) {
            price = Collections.max(prices);
        } else if (range.equalsIgnoreCase("min")) {
            price = Collections.min(prices);
        } else {
            throw new IllegalArgumentException("Range must be 'max' or 'min'");
        }
        return String.valueOf(price);
    }

    /**
     * Retrieves all displayed prices as a list of strings.
     * @return List of price strings (e.g. "$12.99").
     */
    private List<String> priceList() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(PRICE_LIST));
        return driver.findElements(PRICE_LIST).stream()
                .map(WebElement::getText)
                .toList();
    }

    /**
     * Adds the item with the given price to the cart, then proceeds to checkout.
     * @param price The price of the product to add.
     * @return ShoppingCartSummaryPage representing the cart summary after adding item.
     */
    public ShoppingCartSummaryPage addItemWithPriceToCart(String price) throws InterruptedException {
        log("Price: " + price);
        String productToSelectXpath = "//*[contains(@aria-label, 'Price') and contains(text(), '{}')]/ancestor::*[@data-id-product]";
        String addToCartSelector = "[data-button-action='add-to-cart']";

        // Wait briefly for UI stability (could be improved with a more robust wait)
        Thread.sleep(1000);
        WebElement productElement = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath(productToSelectXpath.replace("{}", price)))
        );
        helper.scrollToElement(productElement);
        productElement.click();

        WebElement addToCartButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(addToCartSelector)));
        addToCartButton.click();

        WebElement proceedButton = wait
                .until(ExpectedConditions.elementToBeClickable(PROCEED_TO_CHECKOUT_BUTTON));
        proceedButton.click();

        return new ShoppingCartSummaryPage(driver);
    }

    /**
     * Navigates to the cart summary page.
     * @return ShoppingCartSummaryPage representing the cart summary.
     */
    public ShoppingCartSummaryPage goToCartSummaryPage() {
        WebElement cartLink = wait.until(ExpectedConditions.elementToBeClickable(VIEW_MY_CART));
        cartLink.click();
        return new ShoppingCartSummaryPage(driver);
    }

    /**
     * Gets the product ID of the currently selected product in the cart.
     * @return The data-product-id attribute value.
     * @throws RuntimeException if the element cannot be found or is not present.
     */
    public String getSelectedProductId() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(SELECTED_PRODUCT_LOCATOR));
            WebElement selectedProduct = driver.findElement(SELECTED_PRODUCT_LOCATOR);
            return selectedProduct.getAttribute("data-product-id");
        } catch (TimeoutException | NoSuchElementException e) {
            throw new RuntimeException("Failed to get selected product ID: " + e.getMessage());
        }
    }
}
