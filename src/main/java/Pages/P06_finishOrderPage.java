package Pages;
import Utilities.logUnit;
import Utilities.utilityClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class P06_finishOrderPage {
    private final WebDriver driver;

    private final By finishMSG = By.cssSelector("h2.complete-header");
    public P06_finishOrderPage(WebDriver driver) {
        this.driver = driver;
    }
    public boolean checkvisibleMSG(){
        return driver.findElement(finishMSG).isDisplayed();
    }
}
