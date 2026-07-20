package Admin.Pages;


import Portal.drivers.GUIDriver;
import Portal.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LoginPage {
    private final String LoginEndpoint = "/login";
    //locators
    private final By loginUsername = By.cssSelector("#username");
    private final By loginPassword = By.cssSelector("#password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By brandText = By.cssSelector("span.sidebar-brand-text");


    private GUIDriver driver;

    public LoginPage(GUIDriver driver) {
        this.driver = driver;

    }

    //actions
    @Step("Navigate to Register/Login Page")
    public LoginPage navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlAdmin") + LoginEndpoint);
        return this;
    }

    @Step("Enter name {username} in login field")
    public LoginPage enterUsername(String username) {
        driver.hardWait(1000);
        driver.element().typeSelenium(loginUsername, username);
        return this;
    }

    @Step("Enter password {password} in login field")
    public LoginPage enterLoginPassword(String password) {
        driver.hardWait(1000);
        driver.element().typeSelenium(loginPassword, password);
        return this;
    }

    @Step("Click on login button")
    public LoginPage clickOnLoginBTN() {
        driver.element().clickSelenium(loginButton);
        return this;
    }

    //validations


    public LoginPage assertLoginSuccess() {
        driver.validation().isElementVisible(brandText);
        return this;
    }

}
