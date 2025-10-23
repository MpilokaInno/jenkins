package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class downloadobjects {

    WebDriver driver;
    WebDriverWait wait;

    public downloadobjects(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /*public static final By takealot_lbl = By.xpath("//img[@src=\"/c2a2d966f4555322beb285a531589b8e6e63e887/static/media/src/images/logo.svg-f6ccb489b85bbddf97d6.svg\"]");
    public static final By Download_btn = By.xpath("//a[@data-testid='some-file.txt']");*/

    By emailField = By.id("customer_login_email");
    By passwordField = By.id("customer_login_password");
    By loginButton = By.xpath("//button[@data-ref='dynaform-submit-button']");
    By errorMessage = By.cssSelector("div.message.alert-banner-module_message_2sinO");
    By userGreeting = By.cssSelector("li[data-ref='name-item']");
    By loginFormEmail = By.id("customer_login_email");
    By loginFormPassword = By.id("customer_login_password");

    public void enterEmail(String email) { driver.findElement(emailField).sendKeys(email); }
    public void enterPassword(String password) { driver.findElement(passwordField).sendKeys(password); }
    public void clickLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        // Retry clicking login button up to 2 times if stale
        for (int i = 0; i < 2; i++) {
            try {
                WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
                loginBtn.click();
                System.out.println(" Clicked login button successfully");
                break;
            } catch (StaleElementReferenceException se) {
                System.out.println(" Login button became stale, retrying... (" + (i + 1) + ")");
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            }
        }
        waitForDocumentReady();
    }

    private void waitForDocumentReady() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
    }

    public boolean isDashboardVisible() {
        try {
            WebElement greeting = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(userGreeting));
            String text = greeting.getText() != null ? greeting.getText().trim() : "";
            boolean looksLikeGreeting = text.startsWith("Hi ");
            if (looksLikeGreeting) {
                System.out.println(" Login successful — greeting detected: '" + text + "'");
            } else {
                System.out.println("Greeting element found but text unexpected: '" + text + "'");
            }
            return looksLikeGreeting;
        } catch (TimeoutException e) {
            System.out.println(" Greeting not visible after wait.");
            return false;
        }
    }

    public boolean isLoginFormPresent() {
        try {
            boolean emailPresent = !driver.findElements(loginFormEmail).isEmpty();
            boolean passwordPresent = !driver.findElements(loginFormPassword).isEmpty();
            return emailPresent && passwordPresent;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isErrorVisible() {
        try {
            WebElement error = driver.findElement(errorMessage);
            boolean visible = error.isDisplayed();
            if (visible) {
                System.out.println(" Login failed — error message displayed: " + error.getText());
            }
            return visible;
        } catch (NoSuchElementException e) {
            return false;
        }
    }



    public String getErrorMessage() { return driver.findElement(errorMessage).getText(); }
}
