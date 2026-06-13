package Portal.Pages.components;

import Portal.Pages.SignupLoginPage;
import Portal.drivers.GUIDriver;
import Portal.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationBarComponent {
    private final GUIDriver driver;
    //locators

    private final By loginBTN = By.cssSelector("flt-semantics[role='button'][flt-semantics-identifier='login']");

    public NavigationBarComponent(GUIDriver driver) {
        this.driver = driver;
    }

    @Step("Navigate to Home Page")
    public NavigationBarComponent navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb"));
        return this;
    }

    @Step("Click on login button")
    public SignupLoginPage clickOnLoginBTN() {
        driver.element().click(loginBTN);
        return new SignupLoginPage(driver);
    }
}
