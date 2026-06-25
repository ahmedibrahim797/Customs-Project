package portal.Tests;


import Portal.Pages.components.NavigationBarComponent;
import Portal.drivers.GUIDriver;
import Portal.drivers.UITest;
import Portal.utils.TimeManager;
import Portal.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Account management")
@Feature("PAM")
@Story("User Login")
@Severity(SeverityLevel.CRITICAL)
@Owner("Elnems")
@UITest
public class LoginTest extends BaseTest {

    String timestamp = TimeManager.getSimpleTimestamp();


    @Description("Verify user can login with valid credentials")
    @Test(groups = "authentication")
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
    @BeforeClass(alwaysRun = true)
    protected void preCondition() {
        testData = new JsonReader("login-data");
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();
        //driver.browser().closeExtensionTab();
    }

}
