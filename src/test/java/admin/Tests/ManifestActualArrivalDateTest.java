package admin.Tests;

import Admin.Pages.ManifestActualArrivalDate;
import Portal.drivers.UITest;
import Portal.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import portal.Tests.BaseTest;

@Epic("Manifest Management")
@Feature("Admin")
@Story("Update Manifest Actual Arrival Date")
@Severity(SeverityLevel.CRITICAL)
@Owner("Elnems")
@UITest
public class ManifestActualArrivalDateTest extends BaseTest {

    @Description("Verify admin can update the actual arrival date of a manifest successfully")
    @Test(groups = "manifestActualArrival", dependsOnGroups = "authenticationAdmin")
    public void updateActualArrivalDateTC() {

        new ManifestActualArrivalDate(driver)
                .clickOnManifestListMenu()
                .clickOnAdvancedSearchToggle()
                .typeManifestNumber(testData.getJsonData("manifestNumber"))
                .selectSortDirectionByText(testData.getJsonData("sortDirection"))
                .clickOnAdvancedSearchSubmitButton()
                .clickOnEditButton()
                .setActualArrivalDate(testData.getJsonData("actualArrivalDate"))
                .clickOnSaveButton();
    }


    // Configurations
    @BeforeClass(alwaysRun = true)
    protected void preCondition() {
        testData = new JsonReader("manifest-actual-arrival-date-data");
    }


}
