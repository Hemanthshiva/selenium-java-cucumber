package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import util.SeleniumHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.System.setProperty;

/**
 * @author hemanth.shivashankrappa on 16/06/2018
 * @project interview-test
 */
public class HomePage extends BasePage {

	private SeleniumHelper helper = new SeleniumHelper(driver);

	private static final By DROP_DOWN = By.id("selectProductSort");
	private static final By PROCEED_TO_CHECKOUT_BUTTON = By.xpath("//a[@title='Proceed to checkout']");
	private static final By PRICE_LIST = By.xpath("(//*[@itemprop='name']/following-sibling::div/span[@itemprop='price'])");
	private static final By VIEW_MY_CART = By.xpath("//a[@title='View my shopping cart']");


	HomePage(WebDriver driver) {
		super(driver);
	}


	public HomePage clickOnMenuItem(String item) {
		String menuXpath = "//ul[starts-with(@class,'submenu')]/preceding-sibling::a[contains(text(),'{}')]";
		driver.findElement(By.xpath(menuXpath.replace("{}", item))).click();
		return this;
	}


	public void sortSelection(String sortBy) {
		new Select(driver.findElement(DROP_DOWN)).selectByVisibleText(sortBy);
	}


	public Double getPrice(String range) {
		List<Double> list = priceList();
		double price = 0;
		if (range.equalsIgnoreCase("max")) {
			price = Collections.max(list);
		} else if (range.equalsIgnoreCase("min")) {
			price = Collections.min(list);
		}
		return price;
	}


	private List<Double> priceList() {
		List<Double> priceList = new ArrayList<Double>();
		List<WebElement> listElements = driver.findElements(PRICE_LIST);
		String xpath = "(//*[@itemprop='name']/following-sibling::div/span[@itemprop='price'])";

		for (int i = 1; i <= listElements.size(); i++) {

			String price = driver.findElement(By.xpath(xpath + "[" + i + "]")).getText().replace("$", "");
			priceList.add(Double.valueOf(price));
		}
		return priceList;
	}

	public ShoppingCartSummaryPage addItemWithPriceToCart(String price) {
		String productXpath = "((//*[@itemprop='name']/following-sibling::div/span[@itemprop='price' and contains(text(),'{}')])/following::div/a)[1]";
		String addToCartXpath = "//a[@data-id-product={} and @title='Add to cart']";
		String productId = driver.findElement(By.xpath(productXpath.replace("{}", price))).getAttribute("data-id-product");

		setProperty("productId", productId);
		WebElement element = driver.findElement(By.xpath(productXpath.replace("{}", price)));
		helper.hoverOverElement(element);
		driver.findElement(By.xpath(addToCartXpath.replace("{}", productId))).click();
		boolean isVisible = helper.waitForElementToBeVisible(PROCEED_TO_CHECKOUT_BUTTON);

		if (isVisible)
			driver.findElement(PROCEED_TO_CHECKOUT_BUTTON).click();
		return new ShoppingCartSummaryPage(driver);
	}

	public ShoppingCartSummaryPage goToCartSummaryPage() {
		driver.findElement(VIEW_MY_CART).click();
		return new ShoppingCartSummaryPage(driver);
	}


}
