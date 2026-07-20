package portal.Tests;

import Portal.Pages.CreateBillOfLading;
import Portal.Pages.CreateManifest;
import Portal.drivers.UITest;
import Portal.utils.dataReader.JsonReader;
import Portal.utils.db.DBManager;
import Portal.utils.db.ManifestQueries;
import Portal.utils.logs.LogsManager;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

@Epic("Manifest")
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

    @Description("Verify that acid_number and aci_request_id are populated in manifest_bill_of_ladings after saving the BL")
    @Test(groups = "BillOfLadingFlow", dependsOnGroups = "save-bl")
    public void verifyBillOfLadingAciDataTC() {

        // ── 1. Guard: تأكد إن الـ manifest number و bill number موجودين ──────
        String manifestNumber = ManifestTest.lastCreatedManifestNumber;
        Assert.assertNotNull(manifestNumber,
                "❌ Manifest number is null — ManifestTest may have failed or didn't run first.");
        Assert.assertFalse(manifestNumber.isBlank(),
                "❌ Manifest number is blank.");

        Assert.assertNotNull(lastCreatedBillOfLadingNumber,
                "❌ Bill of Lading number is null — CreateBillOfLadingTest may have failed or didn't run first.");
        Assert.assertFalse(lastCreatedBillOfLadingNumber.isBlank(),
                "❌ Bill of Lading number is blank.");

        // ── 2. جيب الـ manifest UUID من جدول manifests ────────────────────────
        ManifestQueries manifestDB = new ManifestQueries();
        String manifestId = manifestDB.getManifestUUID(manifestNumber);

        Assert.assertNotNull(manifestId,
                "❌ Could not find manifest UUID in DB for manifest_number: [" + manifestNumber + "]");

        // ── 3. ابحث في manifest_bill_of_ladings ───────────────────────────────
        Map<String, String> blRow = manifestDB.getBillOfLadingByManifestIdAndBillNumber(
                manifestId, lastCreatedBillOfLadingNumber);

        Assert.assertFalse(blRow.isEmpty(),
                "❌ No row found in manifest_bill_of_ladings for manifest_id: [" + manifestId
                        + "] and bill_number: [" + lastCreatedBillOfLadingNumber + "]");

        // ── 4. تحقق إن acid_number مش null ────────────────────────────────────
        String acidNumber = blRow.get("acid_number");
        Assert.assertNotNull(acidNumber,
                "❌ acid_number is NULL in manifest_bill_of_ladings for bill_number: ["
                        + lastCreatedBillOfLadingNumber + "]");
        Assert.assertFalse(acidNumber.isBlank(),
                "❌ acid_number is empty/blank for bill_number: [" + lastCreatedBillOfLadingNumber + "]");
        LogsManager.info("✅ acid_number   →", acidNumber);

        // ── 5. تحقق إن aci_request_id مش null ────────────────────────────────
        String aciRequestId = blRow.get("aci_request_id");
        Assert.assertNotNull(aciRequestId,
                "❌ aci_request_id is NULL in manifest_bill_of_ladings for bill_number: ["
                        + lastCreatedBillOfLadingNumber + "]");
        Assert.assertFalse(aciRequestId.isBlank(),
                "❌ aci_request_id is empty/blank for bill_number: [" + lastCreatedBillOfLadingNumber + "]");
        LogsManager.info("✅ aci_request_id →", aciRequestId);
    }


    // ─── Configurations ──────────────────────────────────────────────────────

    @BeforeClass(alwaysRun = true)
    protected void preCondition() {
        testData = new JsonReader("bill-of-lading-data");
    }

    @AfterClass(alwaysRun = true)
    public void closeDBConnection() {
        DBManager.getInstance().closeConnection();
    }


}
