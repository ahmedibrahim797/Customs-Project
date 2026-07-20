package Admin.Pages;

import Portal.drivers.GUIDriver;
import Portal.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class ManifestActualArrivalDate {

    // قائمة المنافيست
    private final By manifestListMenu = By.xpath("(//span[@class='custom-wrap-text' and contains(text(), 'قائمة المنافيست')])[1]");
    // قائمة البحث
    private final By advancedSearchToggleBTN = By.cssSelector("button.advanced-search-toggle");
    // رقم المنافيست
    private final By manifestNumberInput = By.id("manifest_number");
    // ترتيب النتائج
    private final By sortDirectionDropdown = By.id("direction");
    // زر البحث
    private final By advancedSearchSubmitBTN = By.cssSelector("button.advanced-search-btn-submit");
    // زر التعديل
    private final By editBTN = By.cssSelector("a.app-action-btn-edit");
    // تاريخ الوصول الفعلي
    private final By actualArrivalDateInput = By.cssSelector("input[name='actual_arrival_date']");
    // الحفظ
    private final By saveBTN = By.cssSelector("button.app-btn-primary[type='submit']");


    private GUIDriver driver;

    public ManifestActualArrivalDate(GUIDriver driver) {
        this.driver = driver;

    }

    //action
    @Step("Click on 'قائمة المنافيست' (Manifest List) menu item")
    public ManifestActualArrivalDate clickOnManifestListMenu() {
        driver.hardWait(1000);
        driver.element().clickSelenium(manifestListMenu);
        return this;
    }

    @Step("Click on 'بحث متقدم' (Advanced Search) toggle button")
    public ManifestActualArrivalDate clickOnAdvancedSearchToggle() {
        driver.hardWait(1000);
        driver.element().clickSelenium(advancedSearchToggleBTN);
        return this;
    }

    @Step("Type manifest number: '{manifestNumber}' into 'رقم المنافست' search field")
    public ManifestActualArrivalDate typeManifestNumber(String manifestNumber) {
        driver.hardWait(1000);
        driver.element().typeSelenium(manifestNumberInput, manifestNumber);
        return this;
    }

    @Step("Select sort direction by text: '{sortText}' from dropdown")
    public ManifestActualArrivalDate selectSortDirectionByText(String sortText) {
        driver.hardWait(1000);
        // sortText = "الأحدث" أو "الأقدم"
        driver.element().selectFromDropdown(sortDirectionDropdown, sortText);
        return this;
    }

    @Step("Click on 'بحث' (Search) submit button")
    public ManifestActualArrivalDate clickOnAdvancedSearchSubmitButton() {
        driver.hardWait(1000);
        driver.element().clickSelenium(advancedSearchSubmitBTN);
        return this;
    }

    @Step("Click on 'تعديل' (Edit) button")
    public ManifestActualArrivalDate clickOnEditButton() {
        driver.hardWait(1000);
        driver.element().clickSelenium(editBTN);
        return this;
    }

    @Step("Set actual arrival date: '{dateTime}' into arrival date field")
    public ManifestActualArrivalDate setActualArrivalDate(String dateTime) {
        driver.hardWait(1000);
        try {
            LogsManager.info("Setting actual arrival date to: '" + dateTime + "'");
            WebElement input = driver.get().findElement(actualArrivalDateInput);
            JavascriptExecutor js = (JavascriptExecutor) driver.get();
            js.executeScript(
                    "arguments[0].value = arguments[1];" +
                    "arguments[0].dispatchEvent(new Event('input',  {bubbles: true}));" +
                    "arguments[0].dispatchEvent(new Event('change', {bubbles: true}));",
                    input, dateTime
            );
            LogsManager.info("Successfully set actual arrival date to: '" + dateTime + "'");
        } catch (Exception e) {
            LogsManager.error("Failed to set actual arrival date. Value: '" + dateTime + "', Reason: " + e.getMessage());
            throw e;
        }
        return this;
    }

    @Step("Click on 'حفظ' (Save) button")
    public ManifestActualArrivalDate clickOnSaveButton() {
        driver.hardWait(1000);
        driver.element().clickSelenium(saveBTN);
        return this; // تقدر ترجع كلاس صفحة العرض أو القائمة حسب مسار النظام بعد الحفظ
    }

}
