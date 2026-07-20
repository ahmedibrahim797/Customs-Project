package admin.Tests;


import Admin.Pages.LoginPage;
import Portal.drivers.GUIDriver;
import Portal.drivers.UITest;
import Portal.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import portal.Tests.BaseTest;

@Epic("Account management")
@Feature("Admin")
@Story("User Login")
@Severity(SeverityLevel.CRITICAL)
@Owner("Elnems")
@UITest
public class LoginTest extends BaseTest {


    @Description("Verify user can login with valid credentials")
    @Test(groups = "authenticationAdmin")
    public void validLoginAdminTC() {


        new LoginPage(driver).navigate()
                .enterUsername(testData.getJsonData("username"))
                .enterLoginPassword(testData.getJsonData("password"))
                .clickOnLoginBTN()
                .assertLoginSuccess();


    }


    //Configurations
    @BeforeClass(alwaysRun = true)
    protected void preCondition() {
        testData = new JsonReader("login-data-admin");
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = new GUIDriver();
        //driver.browser().closeExtensionTab();
    }

}
