package Portal.Pages.components;

import Portal.drivers.GUIDriver;
import Portal.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;

public class NavigationBarComponent {
    private final GUIDriver driver;
    //locators

    public NavigationBarComponent(GUIDriver driver) {
        this.driver = driver;
    }

    @Step("Navigate to Home Page")
    public NavigationBarComponent navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb"));
        return this;
    }
}
