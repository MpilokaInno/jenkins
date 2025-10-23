package stepDefinitions;

import accelerators.Base;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pageobjects.HomePage;
import pageobjects.ProductPage;
import pageobjects.downloadobjects;
import utility.Utils;

public class cart {

    private final downloadobjects loginPage = new downloadobjects(Base.driver);
    private final HomePage homePage = new HomePage(Base.driver);
    private final ProductPage productPage = new ProductPage(Base.driver);

    @Given("the user is logged in")
    public void the_user_is_logged_in() {
        String baseUrl = Utils.ConfigReader.getProperty("base_url");
        String username = Utils.ConfigReader.getProperty("username");
        String password = Utils.ConfigReader.getProperty("password");
        Base.driver.get(baseUrl);
        loginPage.enterEmail(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
        Assert.assertTrue("Login did not complete successfully", loginPage.isDashboardVisible());
    }

    @When("the user searches for {string}")
    public void the_user_searches_for(String query) {
        homePage.search(query);
    }

    @When("opens the first search result")
    public void opens_the_first_search_result() {
        homePage.openFirstResult();
    }

    @When("adds the product to the cart")
    public void adds_the_product_to_the_cart() {
        productPage.addToCart();
    }

    @Then("the cart should show at least 1 item")
    public void the_cart_should_show_at_least_1_item() {
        boolean drawer = productPage.waitForAddedToCartDrawer();
        if (!drawer) {
            int count = productPage.getCartCount();
            Assert.assertTrue("Cart confirmation not visible and cart count did not increase", count >= 1);
        }
    }
}


