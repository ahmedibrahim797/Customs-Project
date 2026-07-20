package Portal.Pages.components;

import Portal.Pages.CreateManifest;
import Portal.Pages.SignupLoginPage;
import Portal.Pages.SubmitManifest;
import Portal.drivers.GUIDriver;
import Portal.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationBarComponent {
    private final GUIDriver driver;
    //locators

    private final By loginBTN = By.cssSelector("flt-semantics[role='button'][flt-semantics-identifier='login']");
    // الخدمات الجمركية
    private final By customsServicesBTN = By.cssSelector("flt-semantics[role='button'][flt-semantics-identifier='menu_top_customsServices']");
    // المنافيست
    private final By manifestCardBTN = By.cssSelector("flt-semantics[aria-label='manifest'] flt-semantics[flt-semantics-identifier='selectNow']");
    // خدمات المنافيست البحري
    private final By manifestServiceBTN = By.cssSelector("flt-semantics[flt-semantics-identifier='selectNow']");
    //ادراج مانيفست يدوي
    private final By manualManifestBTN = By.cssSelector("flt-semantics[flt-semantics-identifier='img_manual_insert_manifest'] flt-semantics[flt-semantics-identifier='selectNow']");
    // التقديم للجمرك
    private final By submitToCustomsSelectNowBTN = By.xpath("(//flt-semantics[@aria-label='submitting']//flt-semantics[@flt-semantics-identifier='selectNow'])[last()]");

    public NavigationBarComponent(GUIDriver driver) {
        this.driver = driver;
    }

    @Step("Navigate to Home Page")
    public NavigationBarComponent navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb"));
        return this;
    }

    @Step("Click on login button")
    public SignupLoginPage clickOnLoginBTN() {
        driver.hardWait(2000);
        driver.element().clickWithRetry(loginBTN);
        return new SignupLoginPage(driver);
    }

    @Step("Click on Custom Services button")
    public NavigationBarComponent clickOnCustomsServicesBTN() {
        driver.hardWait(2000);
        driver.element().clickWithRetry(customsServicesBTN);
        return this;
    }

    @Step("Click on Manifest card")
    public NavigationBarComponent clickOnManifestCard() {
        driver.element().clickWithRetry(manifestCardBTN);
        return this;
    }

    @Step("Click on Manifest Service button")
    public NavigationBarComponent clickOnManifestService() {
        driver.element().clickWithRetry(manifestServiceBTN);
        return this;
    }

    @Step("Click on Manual Manifest insertion button")
    public CreateManifest clickOnManualManifest() {
        driver.element().clickWithRetry(manualManifestBTN);
        return new CreateManifest(driver);
    }

    @Step("Click on 'اختر الآن' (Select Now) button for 'التقديم إلي الجمرك' (Submitting to Customs)")
    public SubmitManifest clickOnSubmitToCustomsSelectNowButton() {
        driver.element().clickWithRetry(submitToCustomsSelectNowBTN);
        return new SubmitManifest(driver);
    }
}
