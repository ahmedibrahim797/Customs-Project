package Pages;

import Utilities.logUnit;
import Utilities.utilityClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

public class P02_homePage {
    private WebDriver driver;

    public static float totalPrice = 0;
    private static List<WebElement> allProduct;
    private static List<WebElement> selectedProduct;
    private final By addToCartBTNforAllProducts = By.xpath("//button[contains(@class,'btn_inventory')]");
    private final By cartIcon = By.xpath("//span[contains(@class,'shopping_cart_badge')]");
    private final By numSellectedProduct = By.xpath("//button[.='REMOVE']");
    private final By cartIconClick = By.cssSelector("path[fill=\"currentColor\"]");
    private final By priceLable = By.xpath("//button[.=\"REMOVE\"] //preceding-sibling::div[@class='inventory_item_price']");


    public P02_homePage(WebDriver driver){
        this.driver= driver;
    }

    public void selectAllProducts(){
        allProduct = driver.findElements(addToCartBTNforAllProducts);
        logUnit.info("The number of Element : "+allProduct.size());
        for (int i=1; i<=allProduct.size(); i++){
            By addToCartBTNforAllProducts2 = By.xpath("(//button[contains(@class,'btn_inventory')])["+i+"]");
            utilityClass.clickOnElement(driver,addToCartBTNforAllProducts2);
        }
    }
    public String getNumOfProductOnIcon(){
        try{
            logUnit.info("The Number of product in icon : "+utilityClass.getText(driver,cartIcon));
        return utilityClass.getText(driver,cartIcon);
        } catch (Exception e) {
            logUnit.error(e.getMessage());
            return "0";
        }

    }
    public String getNumOfSelectedProducts(){
        try {
            selectedProduct = driver.findElements(numSellectedProduct);
            logUnit.info("The Number of selected product : "+ selectedProduct.size());
            return String.valueOf(selectedProduct.size());
        } catch (Exception e) {
            logUnit.error(e.getMessage());
            return "0";
        }
    }
    public boolean assertNumberOfSelectedProductWithCartIcon(){
        return getNumOfProductOnIcon().equals(getNumOfSelectedProducts());
    }
    public void addRandomProduct(int selectedProduct , int totalOfProduct){
        Set<Integer> products = utilityClass.generateUniqueNum(selectedProduct,totalOfProduct);
        for (int i :products){
            logUnit.info("The Random Number is : "+i);
            By addToCartBTNforAllProducts2 = By.xpath("(//button[contains(@class,'btn_inventory')])["+i+"]");
            utilityClass.clickOnElement(driver,addToCartBTNforAllProducts2);
        }
    }
    public void clickOnCartIcon(){
        utilityClass.clickOnElement(driver,cartIconClick);
    }
    public String getTotalPrice(){
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



}
