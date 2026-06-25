package Portal.drivers;


import Portal.utils.action.AlertActions;
import Portal.utils.action.BrowserActions;
import Portal.utils.action.ElementActions;
import Portal.utils.action.FrameActions;
import Portal.utils.dataReader.PropertyReader;
import Portal.utils.logs.LogsManager;
import Portal.validation.Validation;
import Portal.validation.Verification;
import Portal.utils.WaitManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class GUIDriver {
    private final String browser = PropertyReader.getProperty("browserType");
    private ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public GUIDriver() {
        LogsManager.info("Initializing GUIDriver with browser: " + browser);
        Browser browserType = Browser.valueOf(browser.toUpperCase());
        LogsManager.info("Starting driver for browser: " + browserType);
        AbstractDriver abstractDriver = browserType.getDriverFactory(); //local
        WebDriver driver = ThreadGuard.protect(abstractDriver.createDriver());
        driverThreadLocal.set(driver);
    }

    public ElementActions element() {
        return new ElementActions(get());
    }

    public BrowserActions browser() {
        return new BrowserActions(get());
    }

    public FrameActions frame() {
        return new FrameActions(get());
    }

    public AlertActions alert() {
        return new AlertActions(get());
    }

    //soft assertions
    public Validation validation() {
        return new Validation(get());
    }

    // hard assertions
    public Verification verification() {
        return new Verification(get());
    }

    public WebDriver get() {
        return driverThreadLocal.get();
    }

    public void hardWait(long milliseconds) {
        new WaitManager(get()).hardWait(milliseconds);
    }

    public void quitDriver() {
        driverThreadLocal.get().quit();
    }
}
