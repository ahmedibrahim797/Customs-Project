package Portal.Pages;

import Portal.Pages.components.NavigationBarComponent;
import Portal.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CreateBillOfLading {

    // ادراج بوليصه مانيوال
    private final By insertManualPolicyBTN = By.cssSelector("flt-semantics[flt-semantics-identifier='insertManualPolicy']");
    // نوع الشحنة الرئيسية
    private final By mainShipmentTypeDropdown = By.cssSelector("flt-semantics[flt-semantics-identifier='mainShipmentType'][role='group']");
    // نوع الشحنة الفرعية
    private final By secondShipmentTypeDropdown = By.cssSelector("flt-semantics[flt-semantics-identifier='secondShipmentType'][role='group']");
    // نوع التسليم
    private final By deliveryTypeDropdown = By.cssSelector("flt-semantics[flt-semantics-identifier='deliveryType'][role='group']");
    // رقم البوليصة
    private final By billOfLadingNumberTXT = By.cssSelector("input[aria-label*='رقم البوليصة']");
    // تاريخ البوليصة
    private final By billOfLadingDateTXT = By.cssSelector("input[aria-label*='تاريخ البوليصة']");
    // حسنا
    private final By calendarOkBTN = By.xpath("(//flt-semantics[@role='button' and contains(text(), 'حسنًا')])[last()]");
    // نوع رسم البوليصة
    private final By policyFeeTypeDropdown = By.cssSelector("flt-semantics[flt-semantics-identifier='policyFeeType'][aria-label*='نوع رسم البوليصة']");
    // نوع الواجهة النهائية
    private final By destinationTypeDropdown = By.cssSelector("flt-semantics[flt-semantics-identifier='destinationType'][role='group']");
    // ميناء التفريغ
    private final By dischargePortDropdown = By.cssSelector("flt-semantics[flt-semantics-identifier='dischargePort'][role='group']");
    // دولة ميناء الواجهة
    private final By destinationPortCountryDropdown = By.cssSelector("flt-semantics[aria-label*='دولة ميناء الوجهة'][role='group']");
    // ميناء الواجهة
    private final By portOfDestinationDropdown = By.cssSelector("flt-semantics[aria-label='ميناء الوجهة'][role='group']");
    // ميناء الترانزيت
    private final By transitPortDropdown = By.cssSelector("flt-semantics[flt-semantics-identifier='transitPort'][role='group']");
    // زر التاكيد علي ميناء الترانزيت
    private final By okBTN = By.xpath("(//flt-semantics[@role='button' and contains(text(), 'OK')])[last()]");
    // دولة ميناء الشحن
    private final By shippingPortCountryDropdown = By.cssSelector("flt-semantics[aria-label*='دولة ميناء الشحن'][role='group']");
    // ميناء الشحن
    private final By shippingPortDropdown = By.cssSelector("flt-semantics[aria-label='ميناء الشحن'][role='group']");
    // ادخال acid
    private final By acidIdNumberTXT = By.cssSelector("input[aria-label*='الرقم التعريفي ACID']");
    // اسم المستفيد
    private final By beneficiaryNumberTXT = By.cssSelector("input[aria-label*='رقم المستفيد']");
    // اسم المصدر
    private final By exporterNameTXT = By.cssSelector("input[aria-label*='سم المصدر']");
    // اسم طرف الاخطار
    private final By notifyPartyNameTXT = By.cssSelector("input[aria-label*='اسم طرف الاخطار']");
    // بيانات المصدر
    private final By exporterDetailsTextArea = By.cssSelector("textarea[aria-label*='بيانات المصدر']");
    // بيانات طرف الاخطار
    private final By notifyPartyDetailsTextArea = By.cssSelector("textarea[aria-label*='بيانات طرف الاخطار']");
    // بيانات المستفيد
    private final By beneficiaryDetailsTextArea = By.cssSelector("textarea[aria-label*='بيانات المستفيد']");
    // الملاحظات
    private final By notesTextArea = By.cssSelector("textarea[aria-label*='ملاحظات']");
    // اضافة سطور
    private final By addLinesBTN = By.xpath("(//flt-semantics[@flt-semantics-identifier='addLines'])[last()]");
    // حفظ
    private final By saveBTN = By.xpath("(//flt-semantics[@flt-semantics-identifier='save'])[last()]");
    // البحث برقم البوليصة
    private final By billOfLadingNumberٍSearchTXT = By.cssSelector("input[aria-label*='رقم البوليصة']");
    // الخروج
    private final By exitBTN = By.xpath("(//flt-semantics[@role='button' and contains(text(), 'خروج')])[last()]");

    public NavigationBarComponent navigationBar;
    private GUIDriver driver;

    public CreateBillOfLading(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }

    //action

    @Step("Click on 'إدراج بوالص يدوي' (Insert Manual Policy) button")
    public CreateBillOfLading clickOnInsertManualPolicyButton() {
        driver.hardWait(3000);
        driver.element().clickWithRetry(insertManualPolicyBTN);
        return this;
    }

    @Step("Open 'نوع الشحنة الرئيسية' (Main Shipment Type) dropdown")
    public CreateBillOfLading openMainShipmentTypeDropdown() {
        driver.hardWait(3000);
        driver.element().clickWithRetry(mainShipmentTypeDropdown);
        return this;
    }

    @Step("Open 'نوع الشحنة الفرعية' (Second Shipment Type) dropdown")
    public CreateBillOfLading openSecondShipmentTypeDropdown() {
        driver.hardWait(2000);
        driver.element().clickWithRetry(secondShipmentTypeDropdown);
        return this;
    }

    @Step("Open 'نوع التسليم' (Delivery Type) dropdown")
    public CreateBillOfLading openDeliveryTypeDropdown() {
        driver.hardWait(2000);
        driver.element().clickWithRetry(deliveryTypeDropdown);
        return this;
    }

    // الميثود المسؤولة عن كتابة النص في البحث — مفوّضة لـ ElementActions
    @Step("Search for '{searchValue}' in the active dropdown")
    public CreateBillOfLading searchInDropdown(String searchValue) {
        driver.element().searchInDropdown(searchValue);
        return this;
    }

    // الميثود المسؤولة عن الضغط على اي عنصر — مفوّضة لـ ElementActions
    @Step("Select item '{itemName}' from dropdown results")
    public CreateBillOfLading selectItemFromDropdown(String itemName) {
        driver.element().selectItemFromDropdown(itemName);
        return this;
    }

    @Step("Enter Bill of Lading Number: {blNumber}")
    public CreateBillOfLading enterBillOfLadingNumber(String blNumber) {
        driver.element().type(billOfLadingNumberTXT, blNumber);
        return this;
    }

    @Step("Open 'تاريخ البوليصة' (Bill of Lading Date) picker")
    public CreateBillOfLading openBillOfLadingDatePicker() {
        driver.element().clickNaturally(billOfLadingDateTXT);
        return this;
    }

    // الميثود المسؤولة عن الضغط على اليوم المطلوب
    @Step("Select day {dayNumber} from the calendar")
    public CreateBillOfLading selectDayFromCalendar(String dayNumber) {
        driver.hardWait(1000);
        driver.element().click(driver.element().getDynamicCalendarDayLocator(dayNumber));
        return this;
    }

    @Step("Click on 'حسنًا' (OK) to confirm date selection")
    public CreateBillOfLading confirmDateSelection() {
        driver.hardWait(1000);
        driver.element().click(calendarOkBTN);
        return this;
    }

    @Step("Open 'نوع رسم البوليصة' (Policy Fee Type) dropdown")
    public CreateBillOfLading openPolicyFeeTypeDropdown() {
        driver.hardWait(1000);
        driver.element().clickWithRetry(policyFeeTypeDropdown);
        return this;
    }

    @Step("Open 'نوع الوجهة النهائية' (Destination Type) dropdown")
    public CreateBillOfLading openDestinationTypeDropdown() {
        driver.hardWait(1000);
        driver.element().clickWithRetry(destinationTypeDropdown);
        return this;
    }

    @Step("Open 'ميناء التفريغ' (Discharge Port) dropdown")
    public CreateBillOfLading openDischargePortDropdown() {
        driver.hardWait(2000);
        driver.element().clickWithRetry(dischargePortDropdown);
        return this;
    }

    @Step("Open 'دولة ميناء الوجهة' (Destination Port Country) dropdown")
    public CreateBillOfLading openDestinationPortCountryDropdown() {
        driver.hardWait(2000);
        driver.element().clickWithRetry(destinationPortCountryDropdown);
        return this;
    }

    @Step("Open 'ميناء الوجهة' (Port of Destination) dropdown")
    public CreateBillOfLading openPortOfDestinationDropdown() {
        driver.hardWait(2000);
        driver.element().clickWithRetry(portOfDestinationDropdown);
        return this;
    }

    @Step("Open 'ميناء ترانزيت' (Transit Port) dropdown")
    public CreateBillOfLading openTransitPortDropdown() {
        driver.hardWait(3000);
        driver.element().clickWithRetry(transitPortDropdown);
        return this;
    }

    @Step("Click on 'OK' button")
    public CreateBillOfLading clickOnOkButton() {
        driver.element().click(okBTN);
        return this;
    }

    @Step("Open 'دولة ميناء الشحن' (Shipping Port Country) dropdown")
    public CreateBillOfLading openShippingPortCountryDropdown() {
        driver.hardWait(3000);
        driver.element().clickWithRetry(shippingPortCountryDropdown);
        return this;
    }

    @Step("Open 'ميناء الشحن' (Shipping Port) dropdown")
    public CreateBillOfLading openShippingPortDropdown() {
        driver.hardWait(2000);
        driver.element().clickWithRetry(shippingPortDropdown);
        return this;
    }

    @Step("Enter ACID ID Number: {acidNumber}")
    public CreateBillOfLading enterAcidIdNumber(String acidNumber) {
        driver.element().type(acidIdNumberTXT, acidNumber);
        return this;
    }

    @Step("Enter Beneficiary Number: {beneficiaryNumber}")
    public CreateBillOfLading enterBeneficiaryNumber(String beneficiaryNumber) {
        driver.hardWait(1000);
        driver.element().type(beneficiaryNumberTXT, beneficiaryNumber);
        return this;
    }

    @Step("Enter Exporter Name: {exporterName}")
    public CreateBillOfLading enterExporterName(String exporterName) {
        driver.element().type(exporterNameTXT, exporterName);
        return this;
    }

    @Step("Enter Notify Party Name: {notifyPartyName}")
    public CreateBillOfLading enterNotifyPartyName(String notifyPartyName) {
        driver.element().type(notifyPartyNameTXT, notifyPartyName);
        return this;
    }

    @Step("Enter Exporter Details: {exporterDetails}")
    public CreateBillOfLading enterExporterDetails(String exporterDetails) {
        driver.element().type(exporterDetailsTextArea, exporterDetails);
        return this;
    }

    @Step("Enter Notify Party Details: {notifyPartyDetails}")
    public CreateBillOfLading enterNotifyPartyDetails(String notifyPartyDetails) {
        driver.element().type(notifyPartyDetailsTextArea, notifyPartyDetails);
        return this;
    }

    @Step("Enter Beneficiary Details: {beneficiaryDetails}")
    public CreateBillOfLading enterBeneficiaryDetails(String beneficiaryDetails) {
        driver.element().type(beneficiaryDetailsTextArea, beneficiaryDetails);
        return this;
    }

    @Step("Enter Notes: {notes}")
    public CreateBillOfLading enterNotes(String notes) {
        driver.element().type(notesTextArea, notes);
        return this;
    }

    @Step("Click on 'أضف سطور' (Add Lines) button")
    public CreateBillOfLadingItems clickOnAddLinesButton() {
        driver.hardWait(1000);
        driver.element().scrollElementToCenter(addLinesBTN);
        driver.element().click(addLinesBTN);
        return new CreateBillOfLadingItems(driver);
    }

    @Step("Click on 'حفظ' (Save) button")
    public CreateBillOfLading clickOnSaveButton() {
        driver.element().scrollElementToCenter(saveBTN);
        driver.hardWait(2000);
        driver.element().click(saveBTN);
        return this;
    }

    @Step("Enter Bill of Lading Number: {blNumber}")
    public CreateBillOfLading enterBillOfLadingNumberSearch(String blNumber) {
        driver.hardWait(3000);
        driver.element().type(billOfLadingNumberٍSearchTXT, blNumber);
        return this;
    }

    @Step("Press ENTER key on Bill of Lading Number field")
    public CreateBillOfLading pressEnterOnBillOfLadingNumber() {
        driver.element().pressEnter(billOfLadingNumberٍSearchTXT);
        return this;
    }

    @Step("Click on 'الخروج' (Exit) button")
    public editManifest clickOnExitButton() {
        driver.element().click(exitBTN);
        return new editManifest(driver);
    }


    // validation


    @Step("Verify that 'أضف سطور' (Add Lines) button is visible")
    public CreateBillOfLading verifyAddLinesButtonIsVisible() {
        driver.hardWait(1000);
        driver.validation().isElementVisible(addLinesBTN);
        return this;
    }

    @Step("Verify that Bill of Lading Number '{blNumber}' is displayed successfully in the list")
    public CreateBillOfLading verifyBillOfLadingNumberIsVisible(String blNumber) {
        // 1. إنشاء المحدد الديناميكي للبحث عن رقم البوليصة باستخدام النص
        By dynamicBLNumberElement = By.xpath("(//flt-semantics[contains(text(), '" + blNumber + "')])[last()]");

        // 2. التحقق من ظهور العنصر (Assertion) بنفس الطريقة المطلوبة
        driver.validation().isElementVisible(dynamicBLNumberElement);

        return this;
    }
}
