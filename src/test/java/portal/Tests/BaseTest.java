package portal.Tests;

import Portal.drivers.GUIDriver;
import Portal.drivers.WebDriverProvider;
import Portal.utils.dataReader.JsonReader;
import org.openqa.selenium.WebDriver;

public class BaseTest implements WebDriverProvider {
    protected static GUIDriver driver;
    protected JsonReader testData;


    @Override
    public WebDriver getWebDriver() {
        if (driver == null) {
            return null;
        }
        return driver.get();
    }
}
