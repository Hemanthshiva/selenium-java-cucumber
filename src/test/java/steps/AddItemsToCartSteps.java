package steps;

import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.HomePage;
import pages.LoginPage;
import pages.ShoppingCartSummaryPage;
import utils.TestContext;

/**
 * Step definitions for adding items to cart functionality
 * Handles login, product search, cart operations and verification steps
 */
public class AddItemsToCartSteps {

    private static final String PRICE_SORT_OPTION = "Price, high to low";
    private static final String PRODUCT_NOT_IN_CART_MESSAGE = "The item with id %s is not in the cart";
    private static final String PRODUCT_IN_CART_MESSAGE = "The item with id %s is in the cart";
    private static final String NO_PRODUCT_ID_MESSAGE = "No product ID found. Make sure an item was added to cart first.";

    private final WebDriver driver;
    private final HomePage homePage;
    private final ShoppingCartSummaryPage shoppingCartSummaryPage;

    private String productId;

    public AddItemsToCartSteps(TestContext testContext) {
        this.driver = testContext.getDriver();
        this.homePage = new HomePage(driver);
        this.shoppingCartSummaryPage = new ShoppingCartSummaryPage(driver);
    }

    // Login related steps
    @Given("I login to the application using the below credentials")
    public void loginToApplication(DataTable dataTable) {
        Map<String, String> userCredentials = dataTable.asMaps().get(0);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                userCredentials.get("username"),
                userCredentials.get("password"));
    }

    // Product search and selection steps
    @When("I search for the most expensive {string}")
    public void searchMostExpensiveItem(String item) {
        homePage.clickOnMenuItem(item)
                .sortSelection(PRICE_SORT_OPTION);
    }

    @And("I add the {string} price item to the cart")
    public void addItemToCart(String priceRange) throws InterruptedException {
        String itemPrice = homePage.getPrice(priceRange);
        homePage.addItemWithPriceToCart(itemPrice);
        this.productId = homePage.getSelectedProductId();

        System.out.println("Product ID: " + productId);
    }

    // Cart navigation and verification steps
    @When("I navigate to cart")
    public void navigateToCart() {
        homePage.goToCartSummaryPage();
    }

    @Then("The item should be added to the cart")
    public void verifyItemAddedToCart() {
        verifyItemExistsInCart();
    }

    @Then("I should see the item exist in the cart")
    public void verifyItemExistsInCart() {
        validateProductId();
        Assert.assertTrue(
                String.format(PRODUCT_NOT_IN_CART_MESSAGE, productId),
                shoppingCartSummaryPage.isItemInCart(productId));
    }

    @Then("I should not see the item in the cart")
    public void verifyItemNotInCart() {
        Assert.assertTrue(
                String.format(PRODUCT_IN_CART_MESSAGE, productId),
                shoppingCartSummaryPage.isCartEmpty());
    }

    // Cart manipulation steps
    @When("I delete the item from the cart")
    public void deleteItemFromCart() {
        validateProductId();
        shoppingCartSummaryPage.deleteProductFromCart(productId);
    }

    // Session management steps
    @And("I should log out from the application")
    public void logoutFromApplication() {
        shoppingCartSummaryPage.logOut();
    }

    // Helper methods
    private void validateProductId() {
        if (productId == null) {
            Assert.fail(NO_PRODUCT_ID_MESSAGE);
        }
    }
}