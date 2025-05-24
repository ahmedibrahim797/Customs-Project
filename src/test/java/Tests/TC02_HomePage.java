package Tests;

import Pages.P02_homePage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import testBase.BaseTest;
import TestListener.TestListener;
import java.io.FileNotFoundException;

@Listeners(TestListener.class)
public class TC02_HomePage extends BaseTest {
    P02_homePage homePageOP ;


    @Description("This test case verify Adding items to cart")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ahmed Elnems")
    @Link(name ="Website Url",url ="https://www.saucedemo.com/v1/inventory.html")
    @Epic("Sprint 2")
    @Feature("Cart")
    @Story("Add to Cart")
    @Test(priority = 3)
    public void compareSelectedProductWithNumOfCartIcon() throws FileNotFoundException {

        homePageOP = new P02_homePage(driver);
        homePageOP.selectAllProducts();
        Assert.assertTrue(homePageOP.assertNumberOfSelectedProductWithCartIcon());

    }
    @Description("This test case verify Adding items to cart")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ahmed Elnems")
    @Link(name ="Website Url",url ="https://www.saucedemo.com/v1/inventory.html")
    @Epic("Sprint 2")
    @Feature("Cart")
    @Story("Add to Cart")
    @Test(priority = 2,groups = {"regression"})
    public void compareSelectedRandomProductWithNumOfCartIcon() throws FileNotFoundException, InterruptedException {

        homePageOP = new P02_homePage(driver);
        Thread.sleep(5000);
        homePageOP.addRandomProduct(3,6);
        Assert.assertTrue(homePageOP.assertNumberOfSelectedProductWithCartIcon());

    }
}
