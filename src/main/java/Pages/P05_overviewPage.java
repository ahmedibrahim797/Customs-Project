package Pages;

import Utilities.logUnit;
import Utilities.utilityClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_overviewPage {
    private final By subTotal = By.cssSelector("div.summary_subtotal_label");
    private final By tax = By.cssSelector("div.summary_tax_label");
    private final By total = By.cssSelector("div.summary_total_label");
    private final By finishBTN = By.linkText("FINISH");
    private final WebDriver driver;

    public P05_overviewPage(WebDriver driver) {
        this.driver = driver;
    }
    public float getSubTotal(){
        return Float.parseFloat(utilityClass.getText(driver,subTotal).replace("Item total: $",""));
    }
    public float getTax(){
        return Float.parseFloat(utilityClass.getText(driver,tax).replace("Tax: $",""));
    }
    public float getTotal(){
        logUnit.info("The Actual Total : " + utilityClass.getText(driver,total).replace("Total: $",""));
        return Float.parseFloat(utilityClass.getText(driver,total).replace("Total: $",""));
    }
    public String calculateTotalPrices(){
        logUnit.info("The Calculated Total Price Is : " + (getSubTotal() + getTax()));
        return String.valueOf(getSubTotal() + getTax());
    }
    public boolean comparePrices(){
        return calculateTotalPrices().equals(String.valueOf(getTotal()));
    }

    public void clickOnFinishBTN(){
        utilityClass.clickOnElement(driver,finishBTN);
    }


}
