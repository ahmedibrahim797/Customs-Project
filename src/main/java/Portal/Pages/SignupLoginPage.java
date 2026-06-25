package Portal.Pages;


import Portal.Pages.components.NavigationBarComponent;
import Portal.drivers.GUIDriver;
import Portal.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupLoginPage {
    private final String LoginEndpoint = "/login";
    //locators
    private final By loginUsername = By.cssSelector("input[type='text'][aria-label*='اسم المستخدم']");
    private final By loginPassword = By.cssSelector("input[type='password'][aria-label*='كلمة المرور']");
    private final By loginButton = By.cssSelector("flt-semantics[flt-semantics-identifier='login'][style*='z-index: 11']");
    private final By myAccountBTN = By.cssSelector("flt-semantics[role='button'][flt-semantics-identifier='nav_my_account']");

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
    public SignupLoginPage enterUsername(String username) {
        driver.hardWait(1000);
        driver.element().type(loginUsername, username);
        return this;
    }

    @Step("Enter password {password} in login field")
    public SignupLoginPage enterLoginPassword(String password) {
        driver.hardWait(1000);
        driver.element().type(loginPassword, password);
        return this;
    }

    @Step("Click on login button")
    public SignupLoginPage clickOnLoginBTN() {
        driver.element().click(loginButton);
        return this;
    }

    //validations
    public SignupLoginPage assertLoginPageUrl() {
        driver.hardWait(1000);
        driver.validation().assertPageUrl(PropertyReader.getProperty("baseUrlWeb") + LoginEndpoint);
        return this;
    }

    public SignupLoginPage assertLoginSuccess() {
        driver.validation().isElementVisible(myAccountBTN);
        return this;
    }

}
