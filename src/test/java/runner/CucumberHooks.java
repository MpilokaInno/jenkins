package runner;

import accelerators.Base;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import utility.Utils;
import static accelerators.Base.*;

public class CucumberHooks {

    @Before
    public void initialize() {
        // ✅ Step 1: Start WebDriver first
        Base.initializeDriver();

        // ✅ Step 2: Then open the URL
       // Base.driver.get("https://www.takealot.com/account/login");
    }

    @After
    public void tearDown() {
        Base.quitDriver();
    }
}

/*public class CucumberHooks {
//    	Everything that needs to be done before every scenario is run
    @Before
    public void initialize() throws InterruptedException {
        setUp();
        String baseUrl = Utils.ConfigReader.getProperty("base_url");
        System.out.println("Base URL from config file: " + baseUrl);
        driver.get(baseUrl);
    }

    private void setUp() {
    }

    //    	Everything that needs to be done after every scenario is run
    @After
    public void cleanup() {
        tearDown();
    }

    private void tearDown() {
    }
}*/
