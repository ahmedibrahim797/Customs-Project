package Pages;

import Utilities.utilityClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P04_checkoutPage {
    private final WebDriver driver;
    private final By firstName = By.cssSelector("input#first-name");
    private final By lastName = By.cssSelector("input#last-name");
    private final By postalCodeTXT = By.cssSelector("input#postal-code");
    private final By continueBTN = By.cssSelector("input[value=\"CONTINUE\"]");

    public P04_checkoutPage(WebDriver driver){
        this.driver= driver;
    }
    public void fillCheckoutInfomation(String firstname ,String lastname , String postalcode){
        utilityClass.sendData(driver,firstName,firstname);
        utilityClass.sendData(driver,lastName,lastname);
        utilityClass.sendData(driver,postalCodeTXT,postalcode);
    }
    public void clickOnContinueBTN(){
        utilityClass.clickOnElement(driver,continueBTN);
    }

}
