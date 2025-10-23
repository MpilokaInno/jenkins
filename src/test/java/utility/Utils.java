package utility;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import accelerators.Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utils extends Base {

public class ConfigReader {
    private static Properties properties;
static {
    try (FileInputStream fileInput = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Data\\config.properties"))  {
        properties = new Properties();
        properties.load(fileInput);
    } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException("Failed to load config.properties file.");
    }
}

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

    public static String getTestCaseName(String sTestCase)throws Exception{
        String value = sTestCase;
        try{
            int posi = value.indexOf("@");
            value = value.substring(0, posi);
            posi = value.lastIndexOf(".");
            value = value.substring(posi + 1);
            return value;
        }catch (Exception e){
            throw (e);
        }
    }

    public static void takeScreenshot(WebDriver driver, String sTestCaseName, String path) throws Exception {
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(path + sTestCaseName + ".jpg"));
        } catch (Exception e) {
            throw new Exception();
        }
    }

}
