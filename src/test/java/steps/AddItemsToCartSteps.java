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
 * Step definitions for the "add items to cart" user journey.
 * Handles login, product search, cart operations, and result verification.
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

    /**
     * Authenticates the user with credentials from the scenario data table.
     */
    @Given("I login to the application using the below credentials")
    public void loginToApplication(DataTable dataTable) {
        Map<String, String> userCredentials = dataTable.asMaps().get(0);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
                userCredentials.get("username"),
                userCredentials.get("password")
        );
    }

    /**
     * Navigates and sorts by the most expensive specified product type/category.
     */
    @When("I search for the most expensive {string}")
    public void searchMostExpensiveItem(String item) {
        homePage.clickOnMenuItem(item)
                .sortSelection(PRICE_SORT_OPTION);
    }

    /**
     * Adds the item (based on price range) to cart and stores its product ID.
     */
    @And("I add the {string} price item to the cart")
    public void addItemToCart(String priceRange) throws InterruptedException {
        String itemPrice = homePage.getPrice(priceRange);
        homePage.addItemWithPriceToCart(itemPrice);
        this.productId = homePage.getSelectedProductId();
        System.out.println("Product ID: " + productId);
    }

    /**
     * Proceeds to cart summary page.
     */
    @When("I navigate to cart")
    public void navigateToCart() {
        homePage.goToCartSummaryPage();
    }

    /**
     * Verifies item was added to the cart.
     */
    @Then("The item should be added to the cart")
    public void verifyItemAddedToCart() {
        verifyItemExistsInCart();
    }

    /**
     * Checks product is present in the cart by ID.
     */
    @Then("I should see the item exist in the cart")
    public void verifyItemExistsInCart() {
        validateProductId();
        Assert.assertTrue(
                String.format(PRODUCT_NOT_IN_CART_MESSAGE, productId),
                shoppingCartSummaryPage.isItemInCart(productId)
        );
    }

    /**
     * Verifies the cart is empty.
     */
    @Then("I should not see the item in the cart")
    public void verifyItemNotInCart() {
        Assert.assertTrue(
                String.format(PRODUCT_IN_CART_MESSAGE, productId),
                shoppingCartSummaryPage.isCartEmpty()
        );
    }

    /**
     * Removes the previously added item from the cart.
     */
    @When("I delete the item from the cart")
    public void deleteItemFromCart() {
        validateProductId();
        shoppingCartSummaryPage.deleteProductFromCart(productId);
    }

    /**
     * Logs out from the application.
     */
    @And("I should log out from the application")
    public void logoutFromApplication() {
        shoppingCartSummaryPage.logOut();
    }

    /**
     * Helper method to ensure a productId is available for subsequent steps.
     */
    private void validateProductId() {
        if (productId == null) {
            Assert.fail(NO_PRODUCT_ID_MESSAGE);
        }
    }
}
