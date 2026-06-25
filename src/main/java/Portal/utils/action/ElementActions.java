package Portal.utils.action;


import Portal.utils.WaitManager;
import Portal.utils.logs.LogsManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;

public class ElementActions {
    private final WebDriver driver;
    private WaitManager waitManager;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    //Clicking

    public ElementActions clickSelenium(By locator) {
        waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        // Wait until the element is stable (not moving)
                        Point initialLocation = element.getLocation();
                        LogsManager.info("initialLocation: " + initialLocation);
                        Point finalLocation = element.getLocation();
                        LogsManager.info("finalLocation: " + finalLocation);
                        if (!initialLocation.equals(finalLocation)) {
                            return false; // still moving, wait longer
                        }
                        element.click();
                        LogsManager.info("Clicked on element: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }

    public ElementActions click(By locator) {
        // 1. عداد المحاولات الفاشلة
        final int[] failedAttempts = {0};

        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = d.findElement(locator);

                // الضغط المباشر بالـ JS (الآمن)
                JavascriptExecutor js = (JavascriptExecutor) d;
                js.executeScript("arguments[0].click();", element);

                LogsManager.info("Clicked securely using JS on element: " + locator);
                return true; // نجاح

            } catch (Exception e) {
                failedAttempts[0]++;

                // 2. نقوم بالتمرير فقط إذا فشل السيلينيوم 15 مرة (أي بعد مرور 1.5 ثانية من الانتظار)
                // هذا يعطي الصفحة فرصة للتحميل قبل أن نبدأ في تحريكها
                if (failedAttempts[0] > 15) {
                    LogsManager.info("Element not found yet, doing a gentle scroll...");
                    // استخدمنا ARROW_DOWN بدلاً من PAGE_DOWN لينزل خطوة صغيرة جداً
                    new Actions(d).sendKeys(Keys.ARROW_DOWN).perform();

                    // تصفير العداد لكي لا يمرر الشاشة مرة أخرى إلا بعد 1.5 ثانية أخرى
                    failedAttempts[0] = 0;

                    waitManager.hardWait(200);
                }
                return false; // أعد المحاولة
            }
        });
        return this;
    }


    public ElementActions clickNaturally(By locator) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = d.findElement(locator);

                // التأكد من استقرار العنصر
                Point initialLocation = element.getLocation();
                waitManager.hardWait(50);
                Point finalLocation = element.getLocation();
                if (!initialLocation.equals(finalLocation)) {
                    return false;
                }

                // استخدام Actions لمحاكاة ضغطة ماوس حقيقية (Native Click)
                new Actions(d)
                        .moveToElement(element)
                        .click()
                        .perform();

                LogsManager.info("Clicked naturally using Actions on element: " + locator);
                return true;
            } catch (Exception e) {
                LogsManager.info("Element not ready for native click, scrolling...");
                new Actions(d).sendKeys(Keys.PAGE_DOWN).perform();
                waitManager.hardWait(300);
                return false;
            }
        });
        return this;
    }

    //Typing
//    public ElementActions type(By locator, String text) throws InterruptedException {
//        waitManager.fluentWait().until(d -> {
//            try {
//                System.out.println("start");
//                WebElement element = d.findElement(locator);
//                System.out.println(locator);
//                scrollToElementJS(locator);
//                System.out.println(locator + " scroll");
//                element.click();
//                System.out.println("click");
//                element.sendKeys(Keys.CONTROL + "a");
//                System.out.println("click Keys.CONTROL a");
//                element.sendKeys(Keys.DELETE);
//                System.out.println("click Keys.CONTROL delete");
//                element.sendKeys(text);
//                System.out.println("send text");
//                LogsManager.info("Typed text '" + text + "' into element: " + locator);
//                return true;
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//                return false;
//            }
//        });
//        return this;
//    }
    public ElementActions type(By locator, String text) {
        final int[] failedAttempts = {0};

        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = d.findElement(locator);
                JavascriptExecutor js = (JavascriptExecutor) d;

                // 1. اضغط بـ JS عشان Flutter يجهز الـ input الحقيقي
                js.executeScript("arguments[0].click();", element);

                // 2. جيب الـ active element اللي Flutter ركّز عليه فعلاً
                WebElement activeInput = (WebElement) js.executeScript(
                        "return document.activeElement;"
                );

                // 3. تحقق إن Flutter جهّز input حقيقي وليس مجرد body أو glass-pane
                if (activeInput == null
                        || activeInput.getTagName().equalsIgnoreCase("body")
                        || activeInput.getTagName().equalsIgnoreCase("flt-glass-pane")) {
                    return false; // Flutter لسه ما جهزش، كرر
                }

                // 4. امسح أي نص موجود واكتب في الـ active element الحقيقي
                activeInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                activeInput.sendKeys(Keys.DELETE);
                activeInput.sendKeys(text);

                LogsManager.info("Successfully typed text '" + text + "' into element: " + locator);
                return true;

            } catch (Exception e) {
                failedAttempts[0]++;

                LogsManager.info("فشل المحاولة رقم " + failedAttempts[0] + " والسبب هو: " + e.getMessage());
                if (failedAttempts[0] > 15) {
                    LogsManager.info("Element not ready for typing, doing a gentle scroll...");
                    new Actions(d).sendKeys(Keys.ARROW_DOWN).perform();
                    failedAttempts[0] = 0; // تصفير العداد
                    waitManager.hardWait(200);
                }
                return false;
            }
        });
        return this;
    }

    //hovering
    public ElementActions hover(By locator) {
        waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        new Actions(d).moveToElement(element).perform();
                        LogsManager.info("Hovered over element: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }

    //Getting text
    public String getText(By locator) {
        return waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        String msg = element.getText();
                        LogsManager.info("Retrieved text from element: " + locator + " - Text: " + msg);
                        return !msg.isEmpty() ? msg : null;
                    } catch (Exception e) {
                        return null;
                    }
                }
        );
    }

    //upload file
    public ElementActions uploadFile(By locator, String filePath) {
        String fileAbsolute = System.getProperty("user.dir") + File.separator + filePath;
        waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.sendKeys(fileAbsolute);
                        LogsManager.info("Uploaded file: " + fileAbsolute + " to element: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }


    //find an element
    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    //function to scroll to an element using js
    public void scrollToElementJS(By locator) {
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript(""" 
                        arguments[0].scrollIntoView({behaviour:"auto",block:"center",inline:"center"});""", findElement(locator));
    }

    //select from dropdown
    public ElementActions selectFromDropdown(By locator, String value) {
        waitManager.fluentWait().until(d ->
                {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        Select select = new Select(element);
                        select.selectByVisibleText(value);
                        LogsManager.info("Selected value '" + value + "' from dropdown: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }

}
