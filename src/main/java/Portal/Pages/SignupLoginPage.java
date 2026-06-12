package Portal.Pages;


import Portal.Pages.components.NavigationBarComponent;
import Portal.drivers.GUIDriver;
import Portal.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupLoginPage {
    private final String LoginEndpoint = "/login";
    //locators
    private final By loginUsername = By.xpath("//input[@id='username' and @name='username' and @type='text']");
    private final By loginPassword = By.cssSelector("#current-password");
    private final By loginButton = By.cssSelector("[data-qa=\"login-button\"]");

    public NavigationBarComponent navigationBar;
    private GUIDriver driver;

    public SignupLoginPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }

    //actions
    @Step("Navigate to Register/Login Page")
    public SignupLoginPage navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + LoginEndpoint);
        return this;
    }

    @Step("Enter name {username} in login field")
    public SignupLoginPage enterUsername(String username) throws InterruptedException {
        driver.element().type(loginUsername, username);
        return this;
    }

    @Step("Enter password {password} in login field")
    public SignupLoginPage enterLoginPassword(String password) throws InterruptedException {
        driver.element().type(loginPassword, password);
        return this;
    }

    //validations


}
