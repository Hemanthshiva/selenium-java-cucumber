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
 * @author hemanth.shivashankrappa on 16/06/2018
 * @project interview-test
 */
public class HomePage extends BasePage {

    private SeleniumHelper helper;
    private WebDriverWait wait;

    private static final By DROP_DOWN = By.cssSelector("[aria-label='Sort by selection']");
    private static final By PROCEED_TO_CHECKOUT_BUTTON = By.cssSelector(".cart-content a");
    private static final By PRICE_LIST = By.cssSelector("[aria-label='Price']");
    private static final By VIEW_MY_CART = By.xpath("//a[@title='View my shopping cart']");
    private static final By SELECTED_PRODUCT_LOCATOR = By.cssSelector(".cart-item input");

    public HomePage(WebDriver driver) {
        super(driver);
        this.helper = new SeleniumHelper(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public HomePage clickOnMenuItem(String item) {
        String menuXpath = "//*[@id='top-menu']/li[contains(., '{}')]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(menuXpath.replace("{}", item)))).click();
        return this;
    }

    public void sortSelection(String sortBy) {
        helper.scrollToElement(wait.until(ExpectedConditions.presenceOfElementLocated(DROP_DOWN)));
        wait.until(ExpectedConditions.presenceOfElementLocated(DROP_DOWN)).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(String.format("//a[contains(text(), '%s')]", sortBy)))).click();
    }

    public String getPrice(String range) {
        List<Double> list = priceList().stream()
                .map(s -> Double.parseDouble(s.replace("$", "").trim()))
                .collect(Collectors.toList());
        double price = 0;
        if (range.equalsIgnoreCase("max")) {
            price = Collections.max(list);
        } else if (range.equalsIgnoreCase("min")) {
            price = Collections.min(list);
        }
        return String.valueOf(price);
    }

    private List<String> priceList() {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(PRICE_LIST));
        return driver.findElements(PRICE_LIST).stream()
                .map(WebElement::getText).toList();
    }

    public ShoppingCartSummaryPage addItemWithPriceToCart(String price) throws InterruptedException {
        log("Price: " + price);

        String productToSelect = "//*[contains(@aria-label, 'Price') and contains(text(), '{}')]/ancestor::*[@data-id-product]";
        String addToCartXpath = "[data-button-action='add-to-cart']";

        Thread.sleep(1000);
        WebElement element = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath(productToSelect.replace("{}", price))));
        helper.scrollToElement(element);
        element.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(addToCartXpath))).click();
        wait.until(ExpectedConditions.elementToBeClickable(PROCEED_TO_CHECKOUT_BUTTON)).click();

        return new ShoppingCartSummaryPage(driver);
    }

    public ShoppingCartSummaryPage goToCartSummaryPage() {
        wait.until(ExpectedConditions.elementToBeClickable(VIEW_MY_CART)).click();
        return new ShoppingCartSummaryPage(driver);
    }

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
