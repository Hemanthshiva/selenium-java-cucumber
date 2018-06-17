package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import util.SeleniumHelper;

import static java.lang.System.getProperty;

/**
 * @author hemanth.shivashankrappa on 16/06/2018
 * @project interview-test
 */
public class ShoppingCartSummaryPage extends BasePage {

	private static final By SIGN_OUT_LINK = By.xpath("//a[@title='Log me out']");

	private SeleniumHelper helper = new SeleniumHelper(driver);

	ShoppingCartSummaryPage(WebDriver driver) {
		super(driver);
	}


	public boolean isItemInCart() {
		String product = "//tr[starts-with(@id,'product_{}')]";
		System.out.println(getProperty("productId"));
		boolean isVisible = helper.waitForElementToBeVisible(By.xpath(product.replace("{}", getProperty("productId"))));
		if (isVisible) {
			return true;
		} else {
			return false;
		}
	}

	public ShoppingCartSummaryPage logOut() {
		driver.findElement(SIGN_OUT_LINK).click();
		return this;
	}

	public ShoppingCartSummaryPage deleteProductFromCart(String productId) {
		String deleteProduct = "//tr[starts-with(@id,'product_{}')]//a[@title='Delete']";
		driver.findElement(By.xpath(deleteProduct.replace("{}", productId))).click();
		helper.waitForElementNotVisible(By.xpath(deleteProduct.replace("{}", productId)));
		return this;
	}

}
