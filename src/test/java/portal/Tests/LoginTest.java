package portal.Tests;


import Portal.Pages.components.NavigationBarComponent;
import Portal.drivers.GUIDriver;
import Portal.drivers.UITest;
import Portal.utils.TimeManager;
import Portal.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercise")
@Feature("UI User Management")
@Story("User Login")
@Severity(SeverityLevel.CRITICAL)
@Owner("Ashraf")
@UITest
public class LoginTest extends BaseTest {

    String timestamp = TimeManager.getSimpleTimestamp();


    @Description("Verify user can login with valid credentials")
    @Test
    public void validLoginTC() throws InterruptedException {

        System.out.println("start");

        new NavigationBarComponent(driver).navigate()
                .clickOnLoginBTN()
                .assertLoginPageUrl()
                .enterUsername(testData.getJsonData("username"))
                .enterLoginPassword(testData.getJsonData("password"))
                .clickOnLoginBTN()
                .assertLoginSuccess();


    }


    //Configurations
    @BeforeClass
    protected void preCondition() {
        testData = new JsonReader("login-data");
    }

    @BeforeMethod
    public void setUp() {
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();
        //driver.browser().closeExtensionTab();
    }

    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }
}
