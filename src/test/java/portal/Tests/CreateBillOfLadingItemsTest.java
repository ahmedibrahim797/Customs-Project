package portal.Tests;

import Portal.Pages.CreateBillOfLading;
import Portal.drivers.UITest;
import Portal.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Manifest")
@Feature("Customs Services")
@Story("Create Bill of Lading Items")
@Severity(SeverityLevel.CRITICAL)
@Owner("Elnems")
@UITest
public class CreateBillOfLadingItemsTest extends BaseTest {

    @Description("Add items to the previously filled Bill of Lading")
    @Test(groups = "bill-of-lading-items", dependsOnGroups = "fill-bl")
    public void createBillOfLadingItemsTC() {

        driver.hardWait(2000); // wait for Flutter page to settle

        new CreateBillOfLading(driver)
                .clickOnAddLinesButton()
                .enterContainerCode(testData.getJsonData("$.containerCode"))
                .enterShippingSeal(testData.getJsonData("$.shippingSeal"))
                .openUnitOfPackagesDropdown()
                .searchInDropdown(testData.getJsonData("$.unitOfPackages"))
                .selectItemFromDropdown(testData.getJsonData("$.unitOfPackages"))
                .enterNumberOfPackages(testData.getJsonData("$.numberOfPackages"))
                .enterGrossWeight(testData.getJsonData("$.grossWeight"))
                .enterNetWeight(testData.getJsonData("$.netWeight"))
                .openWeightUnitDropdown()
                .searchInDropdown(testData.getJsonData("$.weightUnit"))
                .selectItemFromDropdown(testData.getJsonData("$.weightUnit"))
                .enterHsCode(testData.getJsonData("$.hsCode"))
                .enterGeneralGoodsDescription(testData.getJsonData("$.generalGoodsDescription"))
                .clickOnSaveButton()
                .verifyContainerCodeIsSaved(testData.getJsonData("$.containerCode"));
    }


    // ─── Configurations ──────────────────────────────────────────────────────

    @BeforeClass(alwaysRun = true)
    protected void preCondition() {
        testData = new JsonReader("bill-of-lading-items-data");
    }
}
