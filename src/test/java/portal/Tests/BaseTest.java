package portal.Tests;

import Portal.drivers.GUIDriver;
import Portal.drivers.WebDriverProvider;
import Portal.utils.dataReader.JsonReader;
import org.openqa.selenium.WebDriver;

public class BaseTest implements WebDriverProvider {
    protected GUIDriver driver;
    protected JsonReader testData;


    @Override
    public WebDriver getWebDriver() {
        return driver.get();
    }
}
