package Utilities;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class utilityClass {

    //TODO:Click on element
    public static void clickOnElement(WebDriver driver , By locator){

        new WebDriverWait(driver , Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();
    }
    //TODO:Send data to element
    public static void sendData(WebDriver driver , By locator ,String text){

        new WebDriverWait(driver , Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).sendKeys(text);
    }
    //TODO:Select Element from Dropdowns by value
    public static void selectElementFromDropdowns(WebDriver driver , By locator ,String text){

        Select select = new Select(byToWebelement(locator,driver));
        select.selectByVisibleText(text);
    }
    //TODO:ExplicitWait
    public static void explicitWait(By locator ,WebDriver driver){
        new WebDriverWait(driver,Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
    //TODO:Convert By to WebElement
    public static WebElement byToWebelement(By locator,WebDriver driver){
        return driver.findElement(locator);
    }
    //TODO:Scrolling Using Js
    public static void scrollingUsingJs(By locator,WebDriver driver){
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",byToWebelement(locator,driver));
        new WebDriverWait(driver , Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    //TODO:Taking Screenshots
    public static void captureScreenshot(WebDriver driver, String testName) {
        // Cast driver to TakesScreenshot
        TakesScreenshot ts = (TakesScreenshot) driver;
        // Capture screenshot and store it as a file
        File source = ts.getScreenshotAs(OutputType.FILE);
        // Define destination file path
        String destination = System.getProperty("user.dir") + "/Test-Output/screenshots/" + testName + " - "+getTimeStamp() +".png";
        try {
            File file = new File(destination);
            FileUtils.copyFile(source, file);
            System.out.println("Screenshot taken: " + destination);
            Allure.addAttachment(testName, Files.newInputStream(Path.of(file.getPath())));
        } catch (IOException e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }

    //TODO:Get Time Stamp
    public static String getTimeStamp(){
        return new SimpleDateFormat("yyyy-MM-dd-h-m-ssa").format(new Date());
    }
    //TODO:Get Text of Element
    public static String getText(WebDriver driver , By locator){

        new WebDriverWait(driver , Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getText();
    }
    //TODO:Generate random number
    public static int generateRandomNumber(int upperbound){
        return new Random().nextInt(upperbound)+1;
    }
    //TODO:Generate Unique Number
    public static Set<Integer> generateUniqueNum(int selectedProduct , int totalOfProduct){
        Set<Integer> products = new HashSet<>();
        while (products.size() < selectedProduct){
            int item = generateRandomNumber(totalOfProduct);
            products.add(item);
        }
        return products;
    }
    //TODO:verify URL
    public static boolean verifyURL(WebDriver driver,String expectedUrl) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return wait.until(ExpectedConditions.urlToBe(expectedUrl));
        } catch (Exception e) {
            return false;
        }

    }
    //TODO:Get all cookies
    public static Set<Cookie> getAllCookies(WebDriver driver){
        return driver.manage().getCookies();
    }

    //TODO:Restore cookies session
    public static void restoreCookie(WebDriver driver,Set<Cookie> cookies){
        for(Cookie cookie:cookies){
            driver.manage().addCookie(cookie);
        }
    }
    //TODO:Inject API
    public static String InjectRequest(){
       return RestAssured.given()
                .contentType("application/json")
                .body("{\"username\":\"ahmed\",\n" +
                        "\"password\":\"YWhtZWQxMDAyMDA=\"}")
                .when()
                .post("https://api.demoblaze.com/signup")
                .then()
                .extract()
                .body()
                .asString();
    }

}
