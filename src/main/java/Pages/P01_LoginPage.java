package Pages;

import Utilities.utilityClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Objects;

public class P01_LoginPage {
    private final WebDriver driver;
    private final By usernameLG = By.cssSelector("input#user-name");
    private final By passwordLG = By.cssSelector("input#password");
    private final By btnLG = By.cssSelector("input#login-button");

    public P01_LoginPage(WebDriver driver){
        this.driver= driver;
    }


    public void setUserName(String username){
        utilityClass.sendData(driver,usernameLG,username);
    }
    public void setPassword(String password){
        utilityClass.sendData(driver,passwordLG,password);
    }
    public void clickBTN(){
        utilityClass.clickOnElement(driver,btnLG);
    }
    public boolean assertLogin(String expected){
        return Objects.equals(driver.getCurrentUrl(), expected);
    }


}
