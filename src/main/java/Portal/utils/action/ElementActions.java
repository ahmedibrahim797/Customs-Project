package Portal.utils.action;


import Portal.utils.WaitManager;
import Portal.utils.logs.LogsManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.time.Duration;

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

    /**
     * يضغط على عنصر Flutter Web بمنطق retry ذكي:
     * - لو لقي العنصر: يضغط عليه مباشرةً بدون أي scroll
     * - لو مش لاقي العنصر: ينتظر ويعيد المحاولة
     * - بدون scrollIntoView عشان ما يسببش Flutter يعيد بناء الـ accessibility tree
     * - مفيد للعناصر اللي بتتشال وبترجع أثناء Flutter re-render
     *
     * @param locator محدد العنصر المراد الضغط عليه
     */
    public ElementActions clickWithRetry(By locator) {
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementNotInteractableException.class)
                .ignoring(ElementClickInterceptedException.class)
                .until(d -> {
                    try {
                        // JS click زي الـ click() الأصلية بدون scrollIntoView
                        WebElement element = d.findElement(locator);
                        JavascriptExecutor js = (JavascriptExecutor) d;
                        js.executeScript("arguments[0].click();", element);
                        LogsManager.info("clickWithRetry: clicked on " + locator);
                        return true;
                    } catch (StaleElementReferenceException e) {
                        LogsManager.info("clickWithRetry: stale element, retrying... " + locator);
                        return false;
                    } catch (NoSuchElementException e) {
                        LogsManager.info("clickWithRetry: element not found yet, waiting... " + locator);
                        return false;
                    } catch (Exception e) {
                        LogsManager.info("clickWithRetry: unexpected error, retrying... " + e.getMessage());
                        return false;
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

    /**
     * typeInFlutterInput — حل مشكلة Flutter web حيث document.activeElement
     * يرجع body أو flt-glass-pane بدل الـ input الحقيقي.
     *
     * الطريقة:
     * 1. كليك JS على الـ semantic element لتنشيط Flutter
     * 2. انتظر قليلاً عشان Flutter يفتح الـ hidden input
     * 3. دور على أول input/textarea ظهر في الـ DOM وأكتب فيه
     *
     * استخدم دي بدل type() في حالة Flutter fields اللي بتعمل scroll بدل ما تكتب.
     */
    public ElementActions typeInFlutterInput(By locator, String text) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement semanticElement = d.findElement(locator);
                JavascriptExecutor js = (JavascriptExecutor) d;

                // Step 1: انقر على الـ semantic element عشان Flutter يفتح الـ real input
                js.executeScript("arguments[0].click();", semanticElement);
                waitManager.hardWait(300); // انتظر Flutter يجهز الـ input

                // Step 2: دور على الـ input الحقيقي اللي Flutter فتحه في الـ DOM
                WebElement realInput = (WebElement) js.executeScript(
                    "var inputs = document.querySelectorAll('input[type=text], input:not([type]), textarea');" +
                    "for (var i = 0; i < inputs.length; i++) {" +
                    "  var style = window.getComputedStyle(inputs[i]);" +
                    "  if (style.display !== 'none' && style.visibility !== 'hidden' && inputs[i].offsetParent !== null) {" +
                    "    return inputs[i];" +
                    "  }" +
                    "}" +
                    "return document.activeElement;"
                );

                if (realInput == null
                        || realInput.getTagName().equalsIgnoreCase("body")
                        || realInput.getTagName().equalsIgnoreCase("flt-glass-pane")) {
                    LogsManager.info("⏳ Flutter input not ready yet, retrying...");
                    return false;
                }

                // Step 3: امسح واكتب
                realInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                realInput.sendKeys(Keys.DELETE);
                realInput.sendKeys(text);

                LogsManager.info("✅ Typed '" + text + "' into Flutter input: " + locator);
                return true;

            } catch (Exception e) {
                LogsManager.info("❌ typeInFlutterInput failed: " + e.getMessage());
                return false;
            }
        });
        return this;
    }

    /**
     * pressEnter — بعد ما تكتب في أي TextField، استخدم الميثود دي لضغط Enter.
     * بتشتغل بنفس منطق type()، بتكليك على العنصر بـ JS عشان Flutter يركّز على الـ real input،
     * وبعدين بتبعت Keys.ENTER على الـ active element.
     *
     * @param locator محدد العنصر (الـ semantic element بتاع Flutter)
     */
    public ElementActions pressEnter(By locator) {
        final int[] failedAttempts = {0};

        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = d.findElement(locator);
                JavascriptExecutor js = (JavascriptExecutor) d;

                // 1. كليك JS عشان Flutter يركّز على الـ real input
                js.executeScript("arguments[0].click();", element);

                // 2. جيب الـ active element اللي Flutter ركّز عليه فعلاً
                WebElement activeInput = (WebElement) js.executeScript(
                        "return document.activeElement;"
                );

                // 3. تحقق إن Flutter جهّز input حقيقي وليس body أو glass-pane
                if (activeInput == null
                        || activeInput.getTagName().equalsIgnoreCase("body")
                        || activeInput.getTagName().equalsIgnoreCase("flt-glass-pane")) {
                    return false; // Flutter لسه ما جهزش، كرر
                }

                // 4. ابعت Enter
                activeInput.sendKeys(Keys.ENTER);

                LogsManager.info("✅ Pressed ENTER on element: " + locator);
                return true;

            } catch (Exception e) {
                failedAttempts[0]++;
                LogsManager.info("❌ pressEnter failed attempt " + failedAttempts[0] + ": " + e.getMessage());

                if (failedAttempts[0] > 15) {
                    new Actions(d).sendKeys(Keys.ARROW_DOWN).perform();
                    failedAttempts[0] = 0;
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

    // ==================== Dynamic Locator Builders ====================

    /**
     * يبني CSS Selector ديناميكي لأي عنصر في القوائم المنسدلة (dropdown items).
     * يُستخدم في أي Page class يحتاج الضغط على نتيجة بعد البحث.
     *
     * @param itemIdentifier  الـ identifier الخاص بالعنصر المطلوب (مثل: اسم الميناء أو أي قيمة)
     * @return By locator جاهز للاستخدام
     */
    public By getDynamicItemLocator(String itemIdentifier) {
        String dynamicSelector = "flt-semantics[flt-semantics-identifier='drop_item_" + itemIdentifier + "']";
        LogsManager.info("Dynamic item locator built: " + dynamicSelector);
        return By.cssSelector(dynamicSelector);
    }

    /**
     * يبني XPath ديناميكي لاختيار يوم معين من أي تقويم في التطبيق.
     * يستخدم starts-with للتمييز بين أرقام الأيام المتشابهة (مثلاً 2 و 20).
     *
     * @param dayNumber رقم اليوم المطلوب كـ String (مثل: "20")
     * @return By locator جاهز للاستخدام
     */
    public By getDynamicCalendarDayLocator(String dayNumber) {
        String dynamicXPath = "//flt-semantics[starts-with(text(), '" + dayNumber + ", ')]";
        LogsManager.info("Dynamic calendar day locator built: " + dynamicXPath);
        return By.xpath(dynamicXPath);
    }

    /**
     * تمرر العنصر إلى منتصف الشاشة بدقة باستخدام JavaScript.
     * مفيدة مع تطبيقات Flutter Web التي تحتاج العنصر في المركز قبل التفاعل معه.
     *
     * @param locator  محدد العنصر المراد تمريره للمركز
     */
    public void scrollElementToCenter(By locator) {
        WebElement element = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
        // انتظار بسيط ليتمكن Flutter من رسم الطبقة الشفافة في المكان الجديد
        new WaitManager(driver).hardWait(300);
    }

    /**
     * يمرر الصفحة لحد العنصر عن طريق scrollIntoView ثم ينتظر ثانيتين قبل أي action.
     * مفيد مع Flutter Web قبل الضغط على الأزرار أو التحقق من العناصر.
     *
     * @param locator محدد العنصر المراد التمرير إليه
     */
    public void scrollToElementAndWait(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
            LogsManager.info("Scrolled to element: " + locator);
        } catch (Exception e) {
            LogsManager.info("Could not scroll to element (not found yet): " + locator);
        }
        new WaitManager(driver).hardWait(2000);
    }

    /**
     * يمرر الصفحة لأسفل في تطبيقات Flutter Web عن طريق إرسال مفاتيح ARROW_DOWN.
     * كل ضغطة ARROW_DOWN تعادل تقريباً 40px، فـ 6 ضغطات ≈ 240px (~6 سم).
     *
     * @param times عدد مرات الضغط على ARROW_DOWN
     */
    public void scrollDownByArrowKeys(int times) {
        Actions actions = new Actions(driver);
        for (int i = 0; i < times; i++) {
            actions.sendKeys(Keys.ARROW_DOWN).perform();
            new WaitManager(driver).hardWait(100);
        }
    }

}
