package accelerators;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static accelerators.Base.driver;

public class actions {
    public static String sTestCaseName;

    public actions(WebDriver driver) {
    }

    private static final String DOWNLOAD_DIR = System.getProperty("user.home") + "/Downloads/"; // Default downloads folder

    public static WebElement getElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void clickOnElement(By locator, String elementName) {
        WebElement element = getElement(locator);
        element.click();
        System.out.println(" Clicked on: " + elementName);
    }

    public static void enterText(By locator, String text, String elementName) {
        WebElement element = getElement(locator);
        element.clear();
        element.sendKeys(text);
        System.out.println(" Entered text in: " + elementName);
    }

    public static void verifyValidLogin() {

        // Step 1: Enter credentials
        enterText(By.id("customer_login_email"), "innocentmpilo1@gmail.com", "Email Field");
        enterText(By.id("customer_login_password"), "ialwaysforgetpassword5", "Password Field");
        clickOnElement(By.xpath("//button[@data-ref='dynaform-submit-button']"), "Login Button");

        // Step 2: Locators for success and error
        By successLocator = By.cssSelector("li[data-ref='name-item'].top-nav-module_name-item_3ROu0");
        By errorLocator = By.cssSelector("div.message.alert-banner-module_message_2sinO");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));



        try {
            // Step 3: Wait for either success OR error, checking every 500ms
            WebElement element = wait.until(driver -> {
                if (!driver.findElements(successLocator).isEmpty()) {
                    return driver.findElement(successLocator);
                }
                if (!driver.findElements(errorLocator).isEmpty()) {
                    return driver.findElement(errorLocator);
                }
                return null; // keep waiting
            });

            // Step 4: Determine which element appeared
            if (element.getAttribute("class").contains("name-item")) {
                String greeting = element.getText().trim();
                System.out.println("Login successful! Greeting: " + greeting);
                Assert.assertTrue("Expected greeting not found!", greeting.contains("Hi Innocent"));
            } else {
                String errorMsg = element.getText().trim();
                System.out.println(" Login failed! Error message: " + errorMsg);
                Assert.assertTrue("Expected error message not found!",
                        errorMsg.contains("Incorrect Email or Password"));
            }

        } catch (TimeoutException e) {
            System.out.println(" Login test timed out — neither success nor error appeared.");
            Assert.fail("Login failed — neither success nor error appeared.");
        }
    }

    //@Test(priority = 1)
   /* public static void verifyValidLogin() {
        // Locators — you may need to inspect Takealot’s login page and update these
        By emailLocator = By.id("customer_login_email");      // placeholder — check actual id/name
        By passwordLocator = By.id("customer_login_password"); // placeholder
        By loginBtnLocator = By.xpath("//button[@data-ref='dynaform-submit-button']");
        By accountMenuLocator = By.cssSelector("img.top-nav-module_logo_R1oac[alt='Takealot']"); // placeholder — something visible after login

        // Enter email
        WebElement email = wait.until(ExpectedConditions.elementToBeClickable(emailLocator));
        email.clear();
        email.sendKeys("innocentmpilo1@gmail.com");

        // Enter password
        WebElement pwd = driver.findElement(passwordLocator);
        pwd.clear();
        pwd.sendKeys("ialwaysforgetpassword5");

        // Click login
        WebElement btn = driver.findElement(loginBtnLocator);
        btn.click();

        // Wait until some post-login element is visible
        WebElement accountMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(accountMenuLocator));

        Assert.assertTrue(accountMenu.isDisplayed(), "Account menu not displayed — login likely failed");
        System.out.println(" Takealot valid-login test passed");
//    }*/

   /* public static void jsClickOnElement(By object, String elementName) {
        try {
            if(!driver.findElements(object).isEmpty()) {
                WebElement webElement = driver.findElement(object);
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", webElement);
            }
            else {
                ExceptionHandler.HandleAssertion("Unable to Click on element " + elementName);
            }
        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to click on:" + elementName);
        }
    }
//    public static void clickOnElement(By object,String elementName) {
//        try {
//            if(!driver.findElements(object).isEmpty()) {
//                driver.findElement(object).click();
//            }
//            else {
//                ExceptionHandler.HandleAssertion("Unable to Click on element " + elementName);
//            }
//        } catch (Exception e) {
//            ExceptionHandler.HandleException(e, "Failed to click on:" + elementName);
//        }
//    }
    public static boolean isMenuSelected(By object,String elementName) {
        boolean selected=false;
        try {
            if(!driver.findElements(object).isEmpty()) {
                selected= true;
            }
            else {
                ExceptionHandler.HandleAssertion("Unable to Display element" + elementName);
            }
        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to select Menu:" + elementName);
        }
        return selected;
    }

    public static void CompareUIContent(String data, By object,String elementName) {
        try {
            String text = getElementText(object,elementName).toLowerCase();
            //				SMSC_ExceptionHandler.HandleAssertion(elementName +" is invalidated (is not equals)");
        }
        catch (Exception e) {
            ExceptionHandler.HandleException(e, "Unable to compare UI Content for " + elementName);
        }
    }
    public static void ComparePDFWithUI(String AtcualText, By object,String elementName) {
        try {
            if(!AtcualText.toLowerCase().contains(getElementText(object,elementName).toLowerCase())) {
                ExceptionHandler.HandleAssertion(elementName +" is invalid");
            }
        }
        catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to compare Pdf and UI : "+elementName);
        }
    }
    public static void ComparePDFWithUserInputData(String AtcualText, String Data,String elementName) {
        try {
            if(!AtcualText.toLowerCase().trim().contains(Data.toLowerCase().trim()))
            {
                ExceptionHandler.HandleAssertion(elementName +" is not valid(not found on the PDF)");
            }
        }
        catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to compare Pdf and Data for : "+elementName);
        }
    }

    public static void CompareValues(String value1, String value2,String elementName) {
        String v1= value1.toLowerCase().trim();
        String v2 =value2.toLowerCase().trim();
        try {
            if(!v1.contains(v2)){
                ExceptionHandler.HandleAssertion(elementName +" is invalidated (has an empty value)");
            }

        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to verify " + elementName );
        }
    }
    public static void CompareData(By object, String value, String elementName) {
        try {
            String text = "";

            if(!driver.findElements(object).isEmpty()) {
                text = driver.findElement(object).getText();
                if(!text.isEmpty()){
                    if(value.trim().isEmpty())
                    {
                        ExceptionHandler.HandleAssertion(elementName +" is invalidated (has an empty value)");
                    }
                }
            }
            else {
                ExceptionHandler.HandleAssertion("Unable to find element " + elementName);
            }
        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to verify " + elementName );
        }
    }

    public static void SwitchTabs() {
        try {
            Set<String> windows = driver.getWindowHandles();
            String sCurrentHandle = driver.getWindowHandle();
            for (String window:windows)
            {
                if(!sCurrentHandle.equalsIgnoreCase(window))
                {
                    driver.switchTo().window(window);
                }
            }
        } catch(Exception e) {
            ExceptionHandler.HandleException(e, "Unable to Switch Tabs");
        }
    }

    //Function to get text
    public static String getElementText(By object,String elementName) {
        String sText="";
        try {
            if(!driver.findElements(object).isEmpty()) {
                sText=driver.findElement(object).getText();
            }
            else {
                ExceptionHandler.HandleAssertion("Unable to find element " + elementName);
            }
        }
        catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to get text from element: " + elementName);
        }
        return sText;
    }

    //Function to type in text box
    public static void typeInTextBox(By object,String data,String elementName) {
        try {
            if(!driver.findElements(object).isEmpty()) {
                driver.findElement(object).clear();
                driver.findElement(object).sendKeys(data);
            }
            else {
                ExceptionHandler.HandleAssertion("Unable to find element " + elementName);
            }
        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to enter data in " + elementName + " textbox");
        }
    }

    public static boolean isElementVisible(By object,String elementName) {
        boolean bFlag = false;
        try {
            if(!driver.findElements(object).isEmpty()) {
                bFlag= true;
            }
        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Unable to check if the " + elementName +" element is visible or not");
        }
        return bFlag;
    }
    public static boolean isElementNotVisible(By object,String elementName) {
        boolean bFlag = false;
        try {
            WebElement element = driver.findElement(object);
            return !element.isDisplayed();
        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Unable to check if the " + elementName +" element is visible or not");
        }
        return bFlag;
    }

//    public static boolean waitForElement(By Locator, long lTime) {
//        try {
//            WebDriverWait wait = new WebDriverWait(driver, lTime);
//            wait.until(ExpectedConditions.elementToBeClickable(Locator));
//            return true;
//        } catch (Exception e) {
//            SMSC_ExceptionHandler.HandleException(e, "Failed to wait for element to be visible");
//            return false;
//        }
//    }
public static boolean waitForElementTextToBePresent(WebDriver driver, By locator, long timeInSeconds, String text) {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds));
        return wait.until(ExpectedConditions.textToBe(locator, text));
    } catch (Exception e) {
        ExceptionHandler.HandleException(e, "Failed to wait for text to be present in element");
        return false; // Return false on failure
    }
}
    public static void waitForElementToBeVisible(By locator, long timeInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Element is not visible");
        }
    }
//    public static boolean waitForElementToBeInvisible(By Locator, long lTime) {
//        try {
//            WebDriverWait wait = new WebDriverWait(driver, lTime);
//            wait.until(ExpectedConditions.invisibilityOfElementLocated(Locator));
//            return true;
//        } catch (Exception e) {
//            SMSC_ExceptionHandler.HandleException(e, "Failed to wait for element to be invisible");
//            return true;
//        }
//    }
    //Get current date in any format
    public static String getCurrentDate(String strFormat)
    {
        try{
            DateFormat dateFormat = new SimpleDateFormat(strFormat);
            Date dateObj = new Date();
            return dateFormat.format(dateObj);
        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to get Current Date:" + strFormat);
            return null;
        }
    }

    //Select by visible text
    public static void selectByVisibleText(By objLocator, String sVisibletext) throws Throwable {
        try {
            if (isElementVisible(objLocator, sVisibletext)) {

                Select s = new Select(driver.findElement(objLocator));
                s.selectByVisibleText(sVisibletext);
            }
            else {
                ExceptionHandler.HandleAssertion("Unable to Select visible text" + sVisibletext);
            }

        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to select visible text: " + sVisibletext);
        }
    }

    //Select by value
    public static void selectByIndex(By objLocator, String sText) throws Throwable {
        try {

            if (isElementVisible(objLocator, sText)) {

                Select s = new Select(driver.findElement(objLocator));
                s.selectByValue(sText);
            }
            else {
                ExceptionHandler.HandleAssertion("Unable to find" + sText);
            }
        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to select value text " + sText);
        }
    }

    //Verify if element is enabled
    public static boolean isElementEnabled(By objLocator) throws Throwable {
        boolean bflag=false;
        try {
            if (driver.findElement(objLocator).isEnabled()) {
                bflag=true;
            }

        } catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to check if element is enabled");
        }
        return bflag;
    }
    public static String GetScreenShot() throws Exception
    {
        String sScreenShotNameWithPath = null;

        try {
            Date oDate = new Date();
            SimpleDateFormat oSDF = new SimpleDateFormat("yyyyMMddHHmmss");
            String sDate = oSDF.format(oDate);

            File fScreenshot = ((TakesScreenshot) Base.driver).getScreenshotAs(OutputType.FILE);
            sScreenShotNameWithPath = System.getProperty("user.dir")+"\\WinDeedData\\Screenshots\\"+"Screenshot_" + sDate + ".png";
            FileUtils.copyFile(fScreenshot, new File(sScreenShotNameWithPath));
        } catch (Exception e) {
            ExceptionHandler.HandleScreenShotException(e, "Failed to get screen shot");
        }

        return sScreenShotNameWithPath;
    }

    public static List<WebElement> getElements(By Obj) throws Throwable{
        List<WebElement> webele=null;
        try {
            webele=driver.findElements(Obj);
        }  catch (Exception e) {
            ExceptionHandler.HandleException(e, "Failed to getElement");
        }
        return webele;
    }

    //Function to clear the textbox
    public static void clearTextbox(By object,String elementName) {
        try {
            if(!driver.findElements(object).isEmpty()) {
                driver.findElement(object).clear();
            } else ExceptionHandler.HandleAssertion("Unable to find Element");
        }
        catch (Exception e) {
            ExceptionHandler.HandleException(e,"Failed to clear text from " + elementName);
        }
    }

    // Method to scroll to the bottom of the page
    public static void scrollToBottom() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(2000);
    }

    public static void clickOnElement(By locator, String fileName) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until the download button is clickable
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

        // Click on the download button
        element.click();
        System.out.println("Clicked download button for file: " + fileName);

        // Wait for the file to be downloaded
        boolean isDownloaded = waitForFileToDownload(fileName, 15);
        assertTrue("File was not downloaded successfully: " + fileName, isDownloaded);
    }

    // Waits for a file to be downloaded
    public static boolean waitForFileToDownload(String fileName, int timeoutSeconds) throws InterruptedException {
        Path filePath = Paths.get(DOWNLOAD_DIR, fileName);
        File file = filePath.toFile();
        int waited = 0;

        while (waited < timeoutSeconds) {
            if (file.exists() && file.length() > 0) { // Ensure file exists and is not empty
                System.out.println("File downloaded successfully: " + file.getAbsolutePath());
                return true;
            }
            Thread.sleep(1000); // Wait 1 second before checking again
            waited++;
        }

        System.out.println("File not found after waiting " + timeoutSeconds + " seconds");
        return false;
    }*/
}


