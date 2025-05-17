package Pages;

import Utilities.logUnit;
import Utilities.utilityClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

public class P03_cartPage {
    public static float totalPrice = 0;
    private WebDriver driver;
    private final By priceLable = By.xpath("//button[.=\"REMOVE\"] //preceding-sibling::div[@class='inventory_item_price']");

    private final By checkoutBTN = By.cssSelector("div a.checkout_button");
    public P03_cartPage(WebDriver driver){
        this.driver= driver;
    }


    public boolean assertCart(String expected){
        return Objects.equals(driver.getCurrentUrl(), expected);
    }
    public String getTotalPriceOnCart(){
        try {
            utilityClass.explicitWait(priceLable,driver);
            List<WebElement> priceAllSelectedProduct = driver.findElements(priceLable);
            for (int i = 1; i <= priceAllSelectedProduct.size(); i++) {
                By elements = By.xpath("(//button[.=\"REMOVE\"] //preceding-sibling::div[@class='inventory_item_price'])[" + i + "]");
                utilityClass.explicitWait(elements,driver);
                String element = utilityClass.getText(driver, elements);
                totalPrice += Float.parseFloat(element.replace("$", ""));
            }
            logUnit.info("Total Price : "+totalPrice);
            return String.valueOf(totalPrice);
        }catch (Exception e){
            logUnit.error(e.getMessage());
            return "0";
        }

    }
    public boolean comparePrices(String price){
        return getTotalPriceOnCart().equals(price);
    }
    public void clickOnCheckoutBTN(){
        utilityClass.clickOnElement(driver,checkoutBTN);
    }

}
