package portal.Tests;

import Portal.Pages.CreateBillOfLading;
import Portal.Pages.CreateManifest;
import Portal.drivers.UITest;
import Portal.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Customs Project")
@Feature("Customs Services")
@Story("Create Bill of Lading")
@Severity(SeverityLevel.CRITICAL)
@Owner("Elnems")
@UITest
public class CreateBillOfLadingTest extends BaseTest {

    // ─── Shared state: BL number produced by this test ───────────────────────
    // static → لأن CreateBillOfLadingItemsTest محتاج يقرأها من class تاني
    public static String lastCreatedBillOfLadingNumber;

    // ─────────────────────────────────────────────────────────────────────────

    @Description("Fill a new Bill of Lading with valid data")
    @Test(groups = "fill-bl", dependsOnGroups = "manifest")
    public void fillBillOfLadingTC() {

        new CreateManifest(driver)
                .clickOnBillOfLadingIcon()
                .clickOnBillOfLadingBTN()
                .clickOnInsertManualPolicyButton()
                .openMainShipmentTypeDropdown()
                .selectItemFromDropdown(testData.getJsonData("$.mainShipmentType"))
                .openSecondShipmentTypeDropdown()
                .selectItemFromDropdown(testData.getJsonData("$.secondShipmentType"))
                .openDeliveryTypeDropdown()
                .searchInDropdown(testData.getJsonData("$.deliveryType"))
                .selectItemFromDropdown(testData.getJsonData("$.deliveryType"))
                .enterBillOfLadingNumber(testData.getJsonData("$.billOfLadingNumber"))
                .openBillOfLadingDatePicker()
                .selectDayFromCalendar(testData.getJsonData("$.billOfLadingDate"))
                .confirmDateSelection()
                .confirmDateSelection()
                .openPolicyFeeTypeDropdown()
                .searchInDropdown(testData.getJsonData("$.policyFeeType"))
                .selectItemFromDropdown(testData.getJsonData("$.policyFeeType"))
                .openDestinationTypeDropdown()
                .selectItemFromDropdown(testData.getJsonData("$.destinationType"))
                .openDischargePortDropdown()
                .searchInDropdown(testData.getJsonData("$.dischargePort"))
                .selectItemFromDropdown(testData.getJsonData("$.dischargePort"))
                .openDestinationPortCountryDropdown()
                .searchInDropdown(testData.getJsonData("$.destinationPortCountry"))
                .selectItemFromDropdown(testData.getJsonData("$.destinationPortCountry"))
                .openPortOfDestinationDropdown()
                .searchInDropdown(testData.getJsonData("$.destinationPort"))
                .selectItemFromDropdown(testData.getJsonData("$.destinationPort"))
                .openTransitPortDropdown()
                .searchInDropdown(testData.getJsonData("$.transitPort"))
                .selectItemFromDropdown(testData.getJsonData("$.transitPort"))
                .clickOnOkButton()
                .openShippingPortCountryDropdown()
                .searchInDropdown(testData.getJsonData("$.shippingPortCountry"))
                .selectItemFromDropdown(testData.getJsonData("$.shippingPortCountry"))
                .openShippingPortDropdown()
                .searchInDropdown(testData.getJsonData("$.shippingPort"))
                .selectItemFromDropdown(testData.getJsonData("$.shippingPort"))
                .enterAcidIdNumber(testData.getJsonData("$.acidIdNumber"))
                .enterBeneficiaryNumber(testData.getJsonData("$.beneficiaryNumber"))
                .enterExporterName(testData.getJsonData("$.exporterName"))
                .enterNotifyPartyName(testData.getJsonData("$.notifyPartyName"))
                .enterExporterDetails(testData.getJsonData("$.exporterDetails"))
                .enterNotifyPartyDetails(testData.getJsonData("$.notifyPartyDetails"))
                .enterBeneficiaryDetails(testData.getJsonData("$.beneficiaryDetails"))
                .enterNotes(testData.getJsonData("$.notes"))
                .verifyAddLinesButtonIsVisible();

        // حفظ رقم البوليصة في static field عشان CreateBillOfLadingItemsTest يقدر يستخدمه
        lastCreatedBillOfLadingNumber = testData.getJsonData("$.billOfLadingNumber");
    }

    @Description("Save the Bill of Lading and verify it appears in the list")
    @Test(groups = "save-bl", dependsOnGroups = "bill-of-lading-items")
    public void saveBillOfLadingTC() {

        new CreateBillOfLading(driver)
                .clickOnSaveButton()
                .enterBillOfLadingNumberSearch(lastCreatedBillOfLadingNumber)
                .pressEnterOnBillOfLadingNumber()
                .verifyBillOfLadingNumberIsVisible(lastCreatedBillOfLadingNumber);
    }


    // ─── Configurations ──────────────────────────────────────────────────────

    @BeforeClass(alwaysRun = true)
    protected void preCondition() {
        testData = new JsonReader("bill-of-lading-data");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        driver.quitDriver();
    }
}
