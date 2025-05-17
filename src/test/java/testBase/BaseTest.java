package testBase;

import Utilities.logUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;
import Utilities.dataUtil;

import java.io.IOException;
import java.time.Duration;
public class BaseTest {
    protected static WebDriver driver;
    private static final String url;

    static {
        try {
            url = dataUtil.getPropertyValue("environmentVariables","LOGIN_URL");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Parameters("browser")
    @BeforeSuite(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser) {
        // Initialize the WebDriver based on the browser parameter
        if (browser.equalsIgnoreCase("chrome")) {
            //System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
            driver = new ChromeDriver();
            logUnit.info("chrome browser is opened");
            driver.get(url);
        } else if (browser.equalsIgnoreCase("firefox")) {
            //System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");
            driver = new FirefoxDriver();
            logUnit.info("firefox browser is opened");
            driver.get(url);
        } else if (browser.equalsIgnoreCase("edge")) {
            //System.setProperty("webdriver.edge.driver", "path/to/edgedriver");
            driver = new EdgeDriver();
            logUnit.info("edge browser is opened");
            driver.get(url);
        } else {
            throw new IllegalArgumentException("Browser \"" + browser + "\" is not supported.");
        }

        // Maximize the window and set a default timeout
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    public WebDriver getDriver() {
        return driver;
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        // Quit the driver after all tests are done
        if (driver != null) {
            driver.quit();
        }
    }
}
