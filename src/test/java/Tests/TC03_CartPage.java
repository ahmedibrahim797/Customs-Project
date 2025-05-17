package Tests;
import Pages.P02_homePage;
import Pages.P03_cartPage;
import Utilities.dataUtil;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import testBase.BaseTest;
import TestListener.TestListener;
import java.io.FileNotFoundException;
import java.io.IOException;

@Listeners(TestListener.class)
public class TC03_CartPage extends BaseTest {
    P02_homePage homePageOP ;
    P03_cartPage cartOBJ;

    public static String totalPrice = "0";

    @Description("This test case verify Cart Page Url")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ahmed Elnems")
    @Link(name ="Website Url",url ="https://www.saucedemo.com/v1/cart.html")
    @Epic("Sprint 3")
    @Feature("Cart")
    @Story("verify Cart Page")
    @Test(priority = 3,groups = {"regression"})
    public void verifyCartPageUrl() throws IOException {
        homePageOP = new P02_homePage(driver);
        totalPrice = homePageOP.getTotalPrice();
        homePageOP.clickOnCartIcon();
        cartOBJ =new P03_cartPage(driver);
        Assert.assertTrue(cartOBJ.assertCart(dataUtil.getPropertyValue("environmentVariables","CART_URL")));
    }
    @Description("This test case verify Price Of Products Added To Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Ahmed Elnems")
    @Link(name ="Website Url",url ="https://www.saucedemo.com/v1/cart.html")
    @Epic("Sprint 3")
    @Feature("Cart")
    @Story("verify Cart Page")
    @Test(priority = 4,groups = {"regression"})
    public void verifyPriceOfProductsAddedToCart(){
        cartOBJ = new P03_cartPage(driver);
        Assert.assertTrue(cartOBJ.comparePrices(totalPrice));

    }
}
