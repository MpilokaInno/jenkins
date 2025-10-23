package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private final By buyboxActions = By.cssSelector("div[data-ref='buybox-actions']");
    private final By addToCartWithinBuybox = By.cssSelector(
            "div[data-ref='buybox'] button[data-ref='add-to-cart-button'], " +
            "div[data-ref='buybox-actions'] button[data-ref='add-to-cart-button'], " +
            "div.pdp-module_sidebar-buybox_1m6Sm button[data-ref='add-to-cart-button']"
    );
    private final By addToCartButton = By.cssSelector("button[data-ref='add-to-cart-button'], button.add-to-cart");
    private final By cartBadge = By.cssSelector("a[data-ref='cart-link'] span[data-ref='cart-count'], span.cart-count");
    private final By cookiesBanner = By.cssSelector("div.cookies-banner-module_cookie-banner_hsodu, div[data-ref='cookie-banner']");
    private final By cookiesAccept = By.cssSelector("button[data-ref='accept-cookies'], button[data-ref='accept-all-cookies'], button[aria-label*='Accept']");
    private final By addedToCartDrawerTitle = By.cssSelector("span[data-ref='drawer-title']");

    public void addToCart() {
        dismissCookieBannerIfPresent();
        waitForDocumentReady();


        WebElement add = null;
        try {
            add = wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartWithinBuybox));
        } catch (TimeoutException e) {
            add = wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartButton));
        }
        // Scroll into view and move cursor over the button
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", add);
        new Actions(driver).moveToElement(add).perform();
        try {
            wait.until(ExpectedConditions.elementToBeClickable(add)).click();
        } catch (ElementClickInterceptedException e) {
            // Fallback: remove cookie banner and click via JS
            forceHideCookieBanner();
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", add);
        }
    }

    private void waitForDocumentReady() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
        } catch (Exception ignored) {}
    }

    private void dismissCookieBannerIfPresent() {
        try {
            // If banner is visible, attempt to click accept
            if (!driver.findElements(cookiesBanner).isEmpty()) {
                WebElement banner = driver.findElement(cookiesBanner);
                if (banner.isDisplayed()) {
                    java.util.List<WebElement> buttons = banner.findElements(cookiesAccept);
                    if (!buttons.isEmpty()) {
                        try {
                            buttons.get(0).click();
                            wait.until(ExpectedConditions.invisibilityOf(banner));
                            return;
                        } catch (Exception ignored) {}
                    }

                    forceHideCookieBanner();
                }
            }
        } catch (Exception ignored) {}
    }

    private void forceHideCookieBanner() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "var b=document.querySelector('div.cookies-banner-module_cookie-banner_hsodu, div[data-ref=\\'cookie-banner\\']');"
                    + "if(b){b.style.display='none'; b.style.visibility='hidden'; b.style.pointerEvents='none';}"
            );
        } catch (Exception ignored) {}
    }

    public boolean waitForAddedToCartDrawer() {
        try {
            WebElement title = new WebDriverWait(driver, Duration.ofSeconds(12))
                    .until(ExpectedConditions.visibilityOfElementLocated(addedToCartDrawerTitle));
            String text = title.getText() == null ? "" : title.getText().trim();
            boolean ok = text.equalsIgnoreCase("Added to cart");
            if (ok) {
                System.out.println(" Added-to-cart drawer confirmed.");
            } else {
                System.out.println(" Drawer title found but text was '" + text + "'.");
            }
            return ok;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public int getCartCount() {
        try {
            WebElement badge = wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge));
            String text = badge.getText().replaceAll("[^0-9]", "");
            if (text.isEmpty()) return 0;
            return Integer.parseInt(text);
        } catch (TimeoutException e) {
            return 0;
        }
    }
}


