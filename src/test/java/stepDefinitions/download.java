package stepDefinitions;

import accelerators.Base;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pageobjects.downloadobjects;
import utility.Utils;

public class download {

    downloadobjects loginPage = new downloadobjects(Base.driver);

    @Given("the user is on the Takealot login page")
    public void open_login_page() {
        String baseUrl = Utils.ConfigReader.getProperty("base_url");
        Base.driver.get(baseUrl);
        System.out.println(" Opened Takealot login page");
    }

    @When("the user enters valid username and password")
    public void enter_valid_credentials() {
        String username = Utils.ConfigReader.getProperty("username");
        String password = Utils.ConfigReader.getProperty("password");
        loginPage.enterEmail(username);
        loginPage.enterPassword(password);
    }



    @When("the user enters invalid username or password")
    public void enter_invalid_credentials() {
        String badUser = Utils.ConfigReader.getProperty("invalid_username") != null ? Utils.ConfigReader.getProperty("invalid_username") : "wrong@example.com";
        String badPass = Utils.ConfigReader.getProperty("invalid_password") != null ? Utils.ConfigReader.getProperty("invalid_password") : "WrongPassword";
        loginPage.enterEmail(badUser);
        loginPage.enterPassword(badPass);
        System.out.println(" Entered invalid credentials");
    }

    @And("clicks the login button")
    public void clicks_the_login_button() {
        loginPage.clickLogin();
    }

    @Then("the user should be logged in successfully")
    public void the_user_should_be_logged_in_successfully() {
        boolean dashboardDetected = loginPage.isDashboardVisible();
        Assert.assertTrue("Dashboard not detected!", dashboardDetected);
        System.out.println(" Dashboard detected — login verified successfully.");
    }


    @Then("the user should see an error message")
    public void verify_error_message() {
        boolean errorVisible = loginPage.isErrorVisible();
        Assert.assertTrue("Error message not displayed!", errorVisible);
        if (errorVisible) {
            System.out.println(" Invalid credentials detected — error message displayed.");
        }
    }
}
