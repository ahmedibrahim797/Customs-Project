package Portal.Pages.components;

import Portal.Pages.CreateManifest;
import Portal.Pages.SignupLoginPage;
import Portal.drivers.GUIDriver;
import Portal.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationBarComponent {
    private final GUIDriver driver;
    //locators

    private final By loginBTN = By.cssSelector("flt-semantics[role='button'][flt-semantics-identifier='login']");
    private final By customsServicesBTN = By.cssSelector("flt-semantics[role='button'][flt-semantics-identifier='menu_top_customsServices']");

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

    @Step("Click on Custom Services button")
    public CreateManifest clickOnCustomsServicesBTN() {
        driver.element().click(customsServicesBTN);
        return new CreateManifest(driver);
    }
}
