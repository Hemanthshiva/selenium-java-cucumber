package steps;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import dto.UserDTO;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.LoginPage;
import pages.ShoppingCartSummaryPage;

import java.util.List;

import static java.lang.System.getProperty;

/**
 * @author hemanth.shivashankrappa on 16/06/2018
 * @project interview-test
 */
public class AddItemsToCartSteps {

    private WebDriver driver;
    private HomePage homePage;
    private ShoppingCartSummaryPage shoppingCartSummaryPage;

    public AddItemsToCartSteps() {
        driver = TestSetup.getDriverInstance();
    }

    @Given("^I login to the application using the below credentials$")
    public void iLoginToTheApplicationUsingTheBelowCredentials(DataTable users) {
        List<UserDTO> user = users.asList(UserDTO.class);

        homePage = new LoginPage(driver).login(user.get(0).getUserName(), user.get(0).getPassword());

    }

    @When("^I search for the most expensive \"([^\"]*)\"$")
    public void iSearchForTheMostExpensive(String item) {
        homePage.clickOnMenuItem(item)
                .sortSelection("Price: Highest first");
    }

    @And("^I add the \"([^\"]*)\" price item to the cart$")
    public void iAddThePriceItemToTheCart(String priceRange) {
        Double price = homePage.getPrice(priceRange);
        shoppingCartSummaryPage = homePage.addItemWithPriceToCart(price.toString());
    }

    @Then("^The item should be added to the cart$")
    public void theItemShouldBeAddedToTheCart() {
        Assert.assertTrue("The item with id " + getProperty("productId") + " is not added to cart",
                shoppingCartSummaryPage.isItemInCart());
    }

    @And("^I should log out from the application$")
    public void iShouldLogOutFromTheApplication() {
        shoppingCartSummaryPage.logOut();
    }

    @When("^I navigate to cart$")
    public void iNavigateToCart() {
        shoppingCartSummaryPage = homePage.goToCartSummaryPage();
    }

    @Then("^I should see the item exist in the cart$")
    public void iShouldSeeTheItemExistInTheCart() {
        Assert.assertTrue("The item with id " + getProperty("productId") + " is not in the cart",
                shoppingCartSummaryPage.isItemInCart());
    }

    @When("^I delete the item from the cart$")
    public void iDeleteTheItemFromTheCart() {
        shoppingCartSummaryPage.deleteProductFromCart(getProperty("productId"));
    }


    @Then("^I should not see the item in the cart$")
    public void iShouldNotSeeTheItemInTheCart() {
        Assert.assertFalse("The item with id " + getProperty("productId") + " is in in the cart",
                shoppingCartSummaryPage.isItemInCart());
    }
}
