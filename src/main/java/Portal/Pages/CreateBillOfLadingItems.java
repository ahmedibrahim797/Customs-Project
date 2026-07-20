package Portal.Pages;

import Portal.Pages.components.NavigationBarComponent;
import Portal.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CreateBillOfLadingItems {

    // رمز الحاوية
    private final By containerCodeTXT = By.cssSelector("input[aria-label*='رمز الحاوية']");
    // السيل الميلاحي
    private final By shippingSealTXT = By.cssSelector("input[aria-label*='السيل الملاحي']");
    // وحدة الطرود
    private final By unitOfPackagesDropdown = By.cssSelector("flt-semantics[aria-label='وحدة الطرود'][role='group']");
    // عدد الطرود
    private final By numberOfPackagesTXT = By.cssSelector("input[aria-label*='عدد الطرود']");
    // الوزن القائم
    private final By grossWeightTXT = By.cssSelector("input[aria-label*='الوزن القائم']");
    // الوزن الصافي
    private final By netWeightTXT = By.cssSelector("input[aria-label*='الوزن الصافي']");
    // وحدة الوزن
    private final By weightUnitDropdown = By.cssSelector("flt-semantics[aria-label='وحدة الوزن'][role='group']");
    // hs code
    private final By hsCodeTXT = By.cssSelector("input[aria-label*='HS']");
    // وصف عام البضاعة
    private final By generalGoodsDescriptionTextArea = By.cssSelector("textarea[aria-label*='وصف عام للبضائع']");
    // زر الحفظ
    private final By saveBTN = By.xpath("(//flt-semantics[@flt-semantics-identifier='save'])[last()]");

    public NavigationBarComponent navigationBar;
    private GUIDriver driver;

    public CreateBillOfLadingItems(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }

    // Actions
    @Step("Enter Container Code: {containerCode}")
    public CreateBillOfLadingItems enterContainerCode(String containerCode) {
        driver.hardWait(3000);
        driver.element().type(containerCodeTXT, containerCode);
        return this;
    }

    @Step("Enter Shipping Seal: {shippingSeal}")
    public CreateBillOfLadingItems enterShippingSeal(String shippingSeal) {
        driver.element().type(shippingSealTXT, shippingSeal);
        return this; // لاحظ أننا نرجع الكلاس الجديد هنا
    }

    @Step("Open 'وحدة الطرود' (Unit of Packages) dropdown")
    public CreateBillOfLadingItems openUnitOfPackagesDropdown() {
        driver.hardWait(2000);
        driver.element().clickWithRetry(unitOfPackagesDropdown);
        return this;
    }

    // الميثود المسؤولة عن كتابة النص في البحث — مفوّضة لـ ElementActions
    @Step("Search for '{searchValue}' in the active dropdown")
    public CreateBillOfLadingItems searchInDropdown(String searchValue) {
        driver.element().searchInDropdown(searchValue);
        return this;
    }

    // الميثود المسؤولة عن الضغط على اي عنصر — مفوّضة لـ ElementActions
    @Step("Select item '{itemName}' from dropdown results")
    public CreateBillOfLadingItems selectItemFromDropdown(String itemName) {
        driver.element().selectItemFromDropdown(itemName);
        return this;
    }

    @Step("Enter Number of Packages: {numberOfPackages}")
    public CreateBillOfLadingItems enterNumberOfPackages(String numberOfPackages) {
        driver.element().type(numberOfPackagesTXT, numberOfPackages);
        return this;
    }

    @Step("Enter Gross Weight: {grossWeight}")
    public CreateBillOfLadingItems enterGrossWeight(String grossWeight) {
        driver.element().type(grossWeightTXT, grossWeight);
        return this;
    }

    @Step("Enter Net Weight: {netWeight}")
    public CreateBillOfLadingItems enterNetWeight(String netWeight) {
        driver.element().type(netWeightTXT, netWeight);
        return this;
    }

    @Step("Open 'وحدة الوزن' (Weight Unit) dropdown")
    public CreateBillOfLadingItems openWeightUnitDropdown() {
        driver.hardWait(2000);
        driver.element().clickWithRetry(weightUnitDropdown);
        return this;
    }

    @Step("Enter HS Code: {hsCode}")
    public CreateBillOfLadingItems enterHsCode(String hsCode) {
        driver.element().type(hsCodeTXT, hsCode);
        return this;
    }

    @Step("Enter General Description of Goods: {description}")
    public CreateBillOfLadingItems enterGeneralGoodsDescription(String description) {
        driver.element().type(generalGoodsDescriptionTextArea, description);
        return this;
    }

    @Step("Click on 'حفظ' (Save) button")
    public CreateBillOfLadingItems clickOnSaveButton() {
        driver.hardWait(1000);
        driver.element().scrollElementToCenter(saveBTN);
        driver.element().click(saveBTN);
        return this;
    }
    // validation

    @Step("Verify that Container Code '{containerCode}' is displayed successfully after saving")
    public CreateBillOfLadingItems verifyContainerCodeIsSaved(String containerCode) {
        // 1. إنشاء المحدد الديناميكي للبحث عن رقم الحاوية
        By dynamicContainerCodeElement = By.xpath("(//flt-semantics[contains(text(), '" + containerCode + "')])[last()]");
        driver.hardWait(1000);
        driver.validation().isElementVisible(dynamicContainerCodeElement);
        driver.hardWait(1000);
        driver.element().scrollElementToCenter(dynamicContainerCodeElement);

        return this;
    }


}
