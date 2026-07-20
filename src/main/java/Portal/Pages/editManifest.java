package Portal.Pages;

import Portal.Pages.components.NavigationBarComponent;
import Portal.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class editManifest {
    // تعديل المنافيس icon
    private final By selectNowBTN = By.xpath("(//flt-semantics[@flt-semantics-identifier='selectNow'])[1]");
    // رصيف التراكي
    private final By trakiPierDropdown = By.cssSelector("flt-semantics[aria-label='رصيف التراكي'][role='group']");
    // التالي
    private final By nextBTN = By.xpath("(//flt-semantics[@flt-semantics-identifier='next'])[last()]");
    // رساله تعديل المنافيست
    private final By editManifestHeader = By.xpath("(//flt-semantics[contains(text(), 'تعديل المنافيست')])[last()]");
    // رسالة التحقق
    private final By successMessage = By.cssSelector("flt-semantics[flt-semantics-identifier*='رقم المنافيست الخاص بك هو']");

    public NavigationBarComponent navigationBar;
    private GUIDriver driver;

    public editManifest(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }

    // action
    @Step("Click on 'اختر الآن' (Select Now) button")
    public editManifest clickOnSelectNowButton() {
        driver.hardWait(1000);
        driver.element().clickWithRetry(selectNowBTN);
        return this;
    }

    @Step("Open 'رصيف التراكي' (Traki Pier) dropdown")
    public editManifest openTrakiPierDropdown() {
        driver.hardWait(1000);
        driver.element().clickWithRetry(trakiPierDropdown);
        return this;
    }

    // الميثود المسؤولة عن كتابة النص في البحث — مفوّضة لـ ElementActions
    @Step("Search for '{searchValue}' in the active dropdown")
    public editManifest searchInDropdown(String searchValue) {
        driver.element().searchInDropdown(searchValue);
        return this;
    }

    // الميثود المسؤولة عن الضغط على اي عنصر — مفوّضة لـ ElementActions
    @Step("Select item '{itemName}' from dropdown results")
    public editManifest selectItemFromDropdown(String itemName) {
        driver.element().selectItemFromDropdown(itemName);
        return this;
    }

    @Step("Click on 'التالي' (Next) button")
    public editManifest clickOnNextButton() {
        driver.hardWait(1000);
        driver.element().clickWithRetry(nextBTN);
        return this;
    }

    // validation
    @Step("Verify that 'تعديل المنافيست' (Edit Manifest) header is visible")
    public editManifest verifyEditManifestHeaderIsVisible() {

        // التحقق من أن العنصر ظاهر على الشاشة
        driver.hardWait(3000);
        driver.validation().isElementVisible(editManifestHeader);

        return this;
    }

    @Step("Verify Manifest creation success message appears")
    public editManifest verifyManifestSuccessMessage() {

        // أ. قراءة النص الفعلي من الشاشة
        // (استخدم طريقة إطار العمل الخاص بك لجلب النص، هذا مجرد مثال)
        String actualMessage = driver.element().getText(successMessage);

        // ب. طباعة الرسالة بالكامل لكي تراها في الـ Console/Report
        System.out.println("الرسالة التي ظهرت بعد التعديل هي: " + actualMessage);

        // نستخدم assertTrue للتحقق من أن الرسالة الفعلية تحتوي على الجزء الثابت
        driver.verification().assertTrue(
                actualMessage.contains("رقم المنافيست الخاص بك هو"),
                "فشل إنشاء المنافيست: رسالة النجاح لم تظهر أو النص غير متطابق! النص الفعلي هو: " + actualMessage
        );

        return this;
    }

}

