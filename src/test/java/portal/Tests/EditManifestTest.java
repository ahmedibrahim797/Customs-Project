package portal.Tests;

import Portal.Pages.CreateBillOfLading;
import Portal.drivers.UITest;
import Portal.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Manifest")
@Feature("Customs Services")
@Story("Edit Manifest")
@Severity(SeverityLevel.CRITICAL)
@Owner("Elnems")
@UITest
public class EditManifestTest extends BaseTest {

    @Description("Exit Bill of Lading list and navigate to Edit Manifest screen")
    @Test(groups = "edit-manifest", dependsOnGroups = "BillOfLadingFlow")

    public void editManifestTC() {

        new CreateBillOfLading(driver)
                .clickOnExitButton()
                .clickOnSelectNowButton()
                .openTrakiPierDropdown()
                .searchInDropdown(testData.getJsonData("trakiPier"))
                .selectItemFromDropdown(testData.getJsonData("trakiPier"))
                .clickOnNextButton()
                .verifyManifestSuccessMessage();

    }

    @BeforeClass(alwaysRun = true)
    protected void preCondition() {
        testData = new JsonReader("manifest-data");
    }
}
