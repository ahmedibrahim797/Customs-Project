package Tests;

import Pages.P01_LoginPage;
import TestListener.TestListener;
import Utilities.dataUtil;
import Utilities.logUnit;
import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import testBase.BaseTest;

import java.io.FileNotFoundException;
import java.io.IOException;

@Listeners(TestListener.class)
public class TC01_LoginPage extends BaseTest {
    P01_LoginPage loginPage;



    @Description("This test case verify that user LOGIN successfully")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ahmed Elnems")
    @Link(name ="Website Url",url ="https://www.saucedemo.com/v1/index.html")
    @Epic("Sprint 1")
    @Feature("Login")
    @Story("Valid Login")
    @Test(priority = 1,groups = {"regression"})
    public void validLoginTC() throws IOException {

        loginPage = new P01_LoginPage(driver);
        loginPage.setUserName(dataUtil.getJsonData("loginData","username"));
        logUnit.info("User Name : "+dataUtil.getJsonData("loginData","username"));
        loginPage.setPassword(dataUtil.getJsonData("loginData","password"));
        logUnit.info("Password : " + dataUtil.getJsonData("loginData","password"));
        loginPage.clickBTN();
        Assert.assertTrue(loginPage.assertLogin(dataUtil.getPropertyValue("environmentVariables","HOME_URL")));
        logUnit.info("The login successfully");

    }

}
