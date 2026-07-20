package portal.Tests;

import Portal.Pages.components.NavigationBarComponent;
import Portal.drivers.UITest;
import Portal.utils.dataReader.JsonReader;
import Portal.utils.db.ManifestQueries;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Timestamp;

import static portal.Tests.ManifestTest.lastCreatedManifestNumber;

@Epic("Manifest")
@Feature("Customs Services")
@Story("Submit Manifest")
@Severity(SeverityLevel.CRITICAL)
@Owner("Elnems")
@UITest
public class SubmissionManifestTest extends BaseTest {

    @Description("Navigate to Submit Manifest screen and submit the manifest to customs")
    @Test(groups = "submit-manifest", dependsOnGroups = "edit-manifest")
    public void submitManifestTC() {
        new NavigationBarComponent(driver)
                .clickOnCustomsServicesBTN()
                .clickOnManifestCard()
                .clickOnManifestService()
                .clickOnSubmitToCustomsSelectNowButton()
                .typeManifestNumber(lastCreatedManifestNumber)
                .openManifestTypeDropdown()
                .searchInDropdown(testData.getJsonData("manifest-type"))
                .selectIncomingOption()
                .clickOnSubmitToCustomsButton()
                .assertManifestListAppear();
    }

    @Description("Verify that manifest_submission_date is populated in the DB after submission")
    @Test(groups = "submit-manifest", dependsOnMethods = "submitManifestTC")
    public void assertManifestSubmissionDateIsNotNull() {
        String manifestNumber = lastCreatedManifestNumber;

        ManifestQueries manifestDB = new ManifestQueries();
        Timestamp submissionDate = manifestDB.getManifestSubmissionDate(manifestNumber);

        Assert.assertNotNull(
                submissionDate,
                "manifest_submission_date يجب أن لا تكون NULL بعد تقديم المانيفست: " + manifestNumber
        );
    }

    @BeforeClass(alwaysRun = true)
    protected void preCondition() {
        testData = new JsonReader("submission-data");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        driver.quitDriver();
    }
}
