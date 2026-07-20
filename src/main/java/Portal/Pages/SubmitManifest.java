package Portal.Pages;

import Portal.Pages.components.NavigationBarComponent;
import Portal.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SubmitManifest {
    // رقم المنافيست
    private final By manifestNumberInput = By.cssSelector("input[aria-label*='رقم المنافيست']");
    // نوع المنافيست
    private final By manifestTypeDropdown = By.cssSelector("flt-semantics[aria-label*='نوع المنافيست'][role='group']");
    // التقديم للجمرك
    private final By submitToCustomsBTN = By.cssSelector("flt-semantics[flt-semantics-identifier='submitToCustoms'][role='button']");
    // manifest list url
    private final String manifestListUrl = "/dashboard/manifest/marine/list";
    // نوع الوارد
    private final By incomingDropItem = By.cssSelector("flt-semantics[flt-semantics-identifier='drop_item_incoming'][role='button']");
    // الاعتماد المباشر على المعرف الخاص بالعنصر
    private final By showManifestList = By.cssSelector("flt-semantics[flt-semantics-identifier='showManifestList']");


    public NavigationBarComponent navigationBar;
    private GUIDriver driver;

    public SubmitManifest(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }

    // action
    @Step("Type manifest number: '{manifestNumber}' into 'رقم المنافيست' field")
    public SubmitManifest typeManifestNumber(String manifestNumber) {
        driver.hardWait(1000);
        driver.element().type(manifestNumberInput, manifestNumber);
        return this;
    }

    @Step("Open 'نوع المنافيست' (Manifest Type) dropdown")
    public SubmitManifest openManifestTypeDropdown() {
        driver.hardWait(1000);
        driver.element().clickWithRetry(manifestTypeDropdown);
        return this;
    }

    // الميثود المسؤولة عن كتابة النص في البحث — مفوّضة لـ ElementActions
    @Step("Search for '{searchValue}' in the active dropdown")
    public SubmitManifest searchInDropdown(String searchValue) {
        driver.element().searchInDropdown(searchValue);
        return this;
    }

    // الميثود المسؤولة عن الضغط على اي عنصر — مفوّضة لـ ElementActions
    @Step("Select item '{itemName}' from dropdown results")
    public SubmitManifest selectItemFromDropdown(String itemName) {
        driver.element().selectItemFromDropdown(itemName);
        return this;
    }

    @Step("Click on 'التقديم للجمرك' (Submit to Customs) button")
    public SubmitManifest clickOnSubmitToCustomsButton() {
        driver.hardWait(1000);
        driver.element().clickWithRetry(submitToCustomsBTN);
        return this;
    }

    @Step("Select 'وارد' (Incoming) from dropdown")
    public SubmitManifest selectIncomingOption() {
        driver.element().clickWithRetry(incomingDropItem);
        return this;
    }

    //validations
    public SubmitManifest assertManifestListAppear() {
        driver.hardWait(4000);
        driver.validation().isElementVisible(showManifestList);
        return this;
    }
}
