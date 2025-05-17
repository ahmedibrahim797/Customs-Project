package Tests;
import Pages.P05_overviewPage;
import Utilities.logUnit;
import Utilities.utilityClass;
import Pages.P03_cartPage;
import Pages.P04_checkoutPage;
import Utilities.dataUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import testBase.BaseTest;
import TestListener.IInvokedListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TC04_CheckoutPage extends BaseTest {
    P03_cartPage cartOBJ;
    P04_checkoutPage checkoutOBJ;
    P05_overviewPage overviewOBJ;

    @Description("This test case verify Checkout url page")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ahmed Elnems")
    @Link(name ="Website Url",url ="https://www.saucedemo.com/v1/checkout-step-two.html")
    @Epic("Sprint 4")
    @Feature("Checkout")
    @Story("verify Checkout Page")
    @Test(priority = 5,groups = {"regression"})
    public void verifyCheckoutPageUrl() throws IOException {
        cartOBJ = new P03_cartPage(driver);
        cartOBJ.clickOnCheckoutBTN();
        checkoutOBJ = new P04_checkoutPage(driver);
        checkoutOBJ.fillCheckoutInfomation(dataUtil.getJsonData("checkout","firstName"),
                dataUtil.getJsonData("checkout","lastName")
                ,dataUtil.getJsonData("checkout","postalCode"));
        logUnit.info(dataUtil.getJsonData("checkout","firstName"));
        logUnit.info(dataUtil.getJsonData("checkout","lastName"));
        logUnit.info(dataUtil.getJsonData("checkout","postalCode"));
        checkoutOBJ.clickOnContinueBTN();
        logUnit.info(driver.getCurrentUrl());
        Assert.assertTrue(utilityClass.verifyURL(driver,dataUtil.getPropertyValue("environmentVariables","OVERVIEWCHECKOUT_URL")));

    }
    @Description("This test case compare Prices")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ahmed Elnems")
    @Link(name ="Website Url",url ="https://www.saucedemo.com/v1/checkout-step-two.html")
    @Epic("Sprint 4")
    @Feature("Checkout")
    @Story("verify Checkout Page")
    @Test(priority = 6,groups = {"regression"})
    public void comparePrices(){
        overviewOBJ = new P05_overviewPage(driver);
        Assert.assertTrue(overviewOBJ.comparePrices());
    }

}
