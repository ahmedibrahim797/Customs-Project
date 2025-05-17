package Tests;
import Pages.P05_overviewPage;
import Pages.P06_finishOrderPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.BaseTest;

public class TC05_finishingOrderPage extends BaseTest {
    P05_overviewPage overviewOBJ;
    P06_finishOrderPage finishOrderOBJ;


    @Description("This test case apply finish Order")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ahmed Elnems")
    @Link(name ="Website Url",url ="https://www.saucedemo.com/v1/checkout-step-two.html")
    @Epic("Sprint 4")
    @Feature("order")
    @Story("complete order")
    @Test(priority = 7,groups = {"regression"})
    public void finishOrderTest(){
        overviewOBJ =new P05_overviewPage(driver);
        overviewOBJ.clickOnFinishBTN();
        finishOrderOBJ = new P06_finishOrderPage(driver);
        Assert.assertTrue(finishOrderOBJ.checkvisibleMSG());

    }

}
