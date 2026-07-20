package Portal.Pages;

import Portal.Pages.components.NavigationBarComponent;
import Portal.drivers.GUIDriver;
import Portal.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CreateManifest {

    // المنافيست
    private final By manifestCardBTN = By.cssSelector("flt-semantics[aria-label='manifest'] flt-semantics[flt-semantics-identifier='selectNow']");
    // خدمات المنافيست البحري
    private final By manifestServiceBTN = By.cssSelector("flt-semantics[flt-semantics-identifier='selectNow']");
    //ادراج مانيفست يدوي
    private final By manualManifestBTN = By.cssSelector("flt-semantics[flt-semantics-identifier='img_manual_insert_manifest'] flt-semantics[flt-semantics-identifier='selectNow']");
    // نوع المنافيست
    private final By manifestTypeDropDownList = By.cssSelector("flt-semantics[flt-semantics-identifier='manifestType'][role='group']");
    // نوع وارد
    private final By manifestImportBTN = By.cssSelector("flt-semantics[flt-semantics-identifier='drop_item_incoming']");
    // ميناء الوصول
    private final By arrivalPortDropDownList = By.cssSelector("flt-semantics[flt-semantics-identifier='arrivalPort'][role='group']");
    // رقم التراكي
    private final By vesselNumberTXT = By.cssSelector("input[type='text'][aria-label*='رقم التراكي']");
    // عرض بيانات المنافيست
    private final By manifestViewBTN = By.cssSelector("flt-semantics[role='button'][flt-semantics-identifier='next']");
    // اختيار وسيلة النقل
    private final By transportationDropDownList = By.cssSelector("flt-semantics[aria-label='إسم وسيلة النقل'][role='group']");
    // دولة ميناء المغادرة
    private final By countryDepartureDropDownList = By.cssSelector("flt-semantics[flt-semantics-identifier='departurePortCountry'][role='group']");
    // ميناء المغادرة
    private final By portDepartureDropDownList = By.cssSelector("flt-semantics[flt-semantics-identifier='portDeparture'][role='group']");
    // تاريخ الوصول المتوقع
    private final By expectedArrivalDateTXT = By.cssSelector("input[aria-label*='تاريخ الوصول المتوقع']");
    // 1. تعريف الـ Locator لزر التأكيد في التقويم
    private final By calendarOkBTN = By.xpath("(//flt-semantics[@role='button' and contains(text(), 'حسنًا')])[last()]");
    // تاريخ المغادرة من دولة القدوم
    private final By departureDateTXT = By.cssSelector("input[type='text'][aria-label*='تاريخ المغادرة من دولة القدوم']");
    // اسم الربان
    private final By captainNameTXT = By.cssSelector("input[type='text'][aria-label*='إسم الربان']");
    // رقم الرحلة
    private final By voyageNumberTXT = By.cssSelector("input[type='text'][aria-label*='رقم الرحلة']");
    // المستودع الرئيسي
    private final By mainWarehouseDropdown = By.cssSelector("flt-semantics[flt-semantics-identifier='mainWarehouse'][role='group']");
    // يوجد ركاب
    private final By havingPassengersCheckbox = By.cssSelector("flt-semantics[flt-semantics-identifier='checkbox_havingPassengers']");
    // عدد الركاب
    private final By numberOfPassengersTXT = By.cssSelector("input[type='text'][aria-label*='عدد الركاب']");
    //الملاحظات
    private final By notesTextArea = By.cssSelector("textarea[aria-label*='الملاحظات']");
    // الحفظ
    private final By universalNextBTN = By.xpath("(//flt-semantics[@flt-semantics-identifier='next'])[last()]");
    // رسالة التحقق
    private final By successMessage = By.cssSelector("flt-semantics[flt-semantics-identifier*='رقم المنافيست الخاص بك هو']");
    // قائمة البوالص
    private final By billOfLadingList = By.xpath("(//flt-semantics[@flt-semantics-identifier='selectNow'])[2]");
    // زر اضافة بوليصة
    private final By billOfLadingBTN = By.cssSelector("flt-semantics[role=\"button\"][flt-semantics-identifier=\"add\"]");


    public NavigationBarComponent navigationBar;
    private GUIDriver driver;

    public CreateManifest(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }

    // ==================== Methods for Navigation Steps ====================


    // ==================== Methods for Manifest Form ====================

    @Step("Open Manifest Type dropdown")
    public CreateManifest openManifestTypeDropdown() {

        driver.element().click(manifestTypeDropDownList);
        return this;
    }

    @Step("Select Manifest Type as Import (وارد)")
    public CreateManifest selectManifestTypeImport() {

        driver.element().click(manifestImportBTN);
        return this;
    }

    @Step("Open Arrival Port dropdown")
    public CreateManifest openArrivalPortDropdown() {
        driver.element().click(arrivalPortDropDownList);
        return this;
    }

    // الميثود المسؤولة عن كتابة النص في البحث — مفوّضة لـ ElementActions
    @Step("Search for '{searchValue}' in the active dropdown")
    public CreateManifest searchInDropdown(String searchValue) {
        driver.element().searchInDropdown(searchValue);
        return this;
    }

    @Step("Enter vessel number (رقم التراكي): {vesselNumber}")
    public CreateManifest enterVesselNumber(String vesselNumber) {
        driver.element().type(vesselNumberTXT, vesselNumber);
        return this;
    }

    @Step("Click on Manifest View button (عرض بيانات المنافيست)")
    public CreateManifest clickOnManifestViewButton() {
        driver.element().click(manifestViewBTN);
        return this;
    }

    @Step("Open Transportation dropdown (وسيلة النقل)")
    public CreateManifest openTransportationDropdown() {
        driver.hardWait(1000);
        driver.element().clickWithRetry(transportationDropDownList);
        return this;
    }

    @Step("Open Country Departure dropdown (دولة ميناء المغادرة)")
    public CreateManifest openCountryDepartureDropdown() {
        driver.hardWait(1000);
        driver.element().clickWithRetry(countryDepartureDropDownList);
        return this;
    }

    @Step("Open Port Departure dropdown (ميناء المغادرة)")
    public CreateManifest openPortDepartureDropdown() {
        driver.hardWait(1000);
        driver.element().clickWithRetry(portDepartureDropDownList);
        return this;
    }

    @Step("Open Expected Arrival Date picker (تاريخ الوصول المتوقع)")
    public CreateManifest openExpectedArrivalDatePicker() {
        driver.hardWait(1000);
        driver.element().clickNaturally(expectedArrivalDateTXT);
        return this;
    }

    // الميثود المسؤولة عن الضغط على اي عنصر — مفوّضة لـ ElementActions
    @Step("Select item '{itemName}' from dropdown results")
    public CreateManifest selectItemFromDropdown(String itemName) {
        driver.element().selectItemFromDropdown(itemName);
        return this;
    }

    // الميثود المسؤولة عن الضغط على اليوم المطلوب
    @Step("Select day {dayNumber} from the calendar")
    public CreateManifest selectDayFromCalendar(String dayNumber) {
        driver.hardWait(1000);
        driver.element().click(driver.element().getDynamicCalendarDayLocator(dayNumber));
        return this;
    }

    @Step("Click on 'حسنًا' (OK) to confirm date selection")
    public CreateManifest confirmDateSelection() {
        driver.hardWait(1000);
        driver.element().click(calendarOkBTN);
        return this;
    }

    @Step("Open Departure Date picker")
    public CreateManifest openDepartureDatePicker() {
        driver.element().clickNaturally(departureDateTXT);
        return this;
    }

    @Step("Enter captain name: {captainName}")
    public CreateManifest enterCaptainName(String captainName) {
        driver.element().type(captainNameTXT, captainName);
        return this;
    }

    @Step("Enter voyage number: {voyageNumber}")
    public CreateManifest enterVoyageNumber(String voyageNumber) {
        driver.element().type(voyageNumberTXT, voyageNumber);
        return this;
    }

    @Step("Open Main Warehouse dropdown")
    public CreateManifest openMainWarehouseDropdown() {
        driver.element().click(mainWarehouseDropdown);
        return this;
    }

    // 2. إنشاء ميثود ذكية لتحديد الخيار (تضغط عليه فقط إذا لم يكن محددًا)
    @Step("Check 'يوجد ركاب' (Having Passengers) if not already checked")
    public CreateManifest checkHavingPassengers() {
        driver.hardWait(2000);
        WebElement checkbox = driver.element().findElement(havingPassengersCheckbox);

        // نتحقق من حالة الصندوق عبر خاصية aria-checked
        String isChecked = checkbox.getAttribute("aria-checked");

        // إذا كانت الحالة false (غير محدد)، نضغط عليه
        if ("false".equals(isChecked)) {
            driver.element().click(havingPassengersCheckbox);
        }
        return this;
    }

    @Step("Enter number of passengers: {passengersCount}")
    public CreateManifest enterNumberOfPassengers(String passengersCount) {
        driver.element().type(numberOfPassengersTXT, passengersCount);
        return this;
    }

    @Step("Enter notes: {notes}")
    public CreateManifest enterNotes(String notes) {
        driver.element().type(notesTextArea, notes);
        return this;
    }

    @Step("Click on active 'التالي' (Next) button")
    public CreateManifest clickOnNextButton() {
        driver.element().click(universalNextBTN);
        return this;
    }

    @Step("Get Manifest creation success message")
    public String getManifestSuccessMessage() {
        // إطار العمل الخاص بك (مثل SHAFT) غالباً ينتظر العنصر تلقائياً قبل جلب النص
        return driver.element().getText(successMessage);
    }

    @Step("Extract Manifest Number from success message")
    public String getGeneratedManifestNumber() {
        String fullMessage = getManifestSuccessMessage();
        // fullMessage = "رقم المنافيست الخاص بك هو : 600/2026/000131 - وارد"

        // ستقوم بقص النص بناءً على المسافات أو الرموز لاستخراج الرقم فقط
        // مثال بسيط:
        String[] parts = fullMessage.split(" : ");
        String numberPart = parts[1].split(" - ")[0]; // سيستخرج "600/2026/000131"

        LogsManager.info("The Manifest Number is : " + numberPart);

        return numberPart;
    }

    // validation
    @Step("Message is displayed")
    public CreateManifest assertMessageDisplayed() {
        driver.validation().isElementVisible(successMessage);
        return this;
    }

    @Step("Verify Manifest creation success message appears")
    public CreateManifest verifyManifestSuccessMessage() {

        // أ. قراءة النص الفعلي من الشاشة
        // (استخدم طريقة إطار العمل الخاص بك لجلب النص، هذا مجرد مثال)
        String actualMessage = driver.element().getText(successMessage);

        // ب. طباعة الرسالة بالكامل لكي تراها في الـ Console/Report
        System.out.println("الرسالة التي ظهرت بعد الحفظ هي: " + actualMessage);

        // نستخدم assertTrue للتحقق من أن الرسالة الفعلية تحتوي على الجزء الثابت
        driver.verification().assertTrue(
                actualMessage.contains("رقم المنافيست الخاص بك هو"),
                "فشل إنشاء المنافيست: رسالة النجاح لم تظهر أو النص غير متطابق! النص الفعلي هو: " + actualMessage
        );

        return this;
    }


    @Step("Click on bill of lading list icon")
    public CreateManifest clickOnBillOfLadingIcon() {
        driver.element().click(billOfLadingList);
        return this;
    }

    @Step("Click on bill of lading Button")
    public CreateBillOfLading clickOnBillOfLadingBTN() {
        driver.hardWait(4000);
        driver.element().clickWithRetry(billOfLadingBTN);
        return new CreateBillOfLading(driver);
    }

}


