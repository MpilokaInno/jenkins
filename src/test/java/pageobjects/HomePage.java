package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private final By searchContainer = By.cssSelector("div[data-ref='search-box']");
    private final By searchInput = By.cssSelector("div[data-ref='search-box'] input.search-field, input[name='search'].search-field");
    private final By searchButton = By.cssSelector("button[data-ref='search-submit-button']");
    private final By productCard = By.cssSelector("article[data-ref='product-card']");
    private final By productLinkUnderlay = By.cssSelector("a.product-card-module_link-underlay_3sfaA, a[title='Go to product details'][data-react-link='true']");

    public void search(String query) {

       // wait.until(ExpectedConditions.visibilityOfElementLocated(searchContainer));
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        input.clear();
        input.sendKeys(query);
        try {
            driver.findElement(searchButton).click();
        } catch (NoSuchElementException e) {
            input.sendKeys(Keys.ENTER);
        }
    }

    public void openFirstResult() {
        // Wait for product cards, then click the underlay link in the first visible card
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productCard));
        java.util.List<WebElement> cards = driver.findElements(productCard);
        WebElement firstCard = cards.stream().filter(WebElement::isDisplayed).findFirst()
                .orElseThrow(() -> new NoSuchElementException("No visible product card"));
        WebElement link = firstCard.findElement(productLinkUnderlay);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", link);
        wait.until(ExpectedConditions.elementToBeClickable(link)).click();

        // If it opened a new tab/window, switch to it
        java.util.Set<String> handles = driver.getWindowHandles();
        if (handles.size() > 1) {
            String current = driver.getWindowHandle();
            for (String h : handles) {
                if (!h.equals(current)) {
                    driver.switchTo().window(h);
                    break;
                }
            }
        }
    }
}


