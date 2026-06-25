package portal.Tests;


import Portal.Pages.CreateManifest;
import Portal.Pages.components.NavigationBarComponent;
import Portal.drivers.UITest;
import Portal.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Customs Project")
@Feature("Customs Services")
@Story("Create Manifest")
@Severity(SeverityLevel.CRITICAL)
@Owner("Elnems")
@UITest
public class ManifestTest extends BaseTest {


    @Description("Create a new manifest with valid data")
    @Test(groups = "manifest", dependsOnGroups = "authentication")
    public void createManifestTC() {

        new NavigationBarComponent(driver)
                .clickOnCustomsServicesBTN()
                .clickOnManifestCard()
                .clickOnManifestService()
                .clickOnManualManifest();

        driver.hardWait(4000); // wait for Flutter page transition

        new CreateManifest(driver)
                // ملء الفورم
                //.refreshPageToLoadSemantics()
                .openManifestTypeDropdown()
                .selectManifestTypeImport()
                .openArrivalPortDropdown()
                .searchInDropdown(testData.getJsonData("arrivalPort"))
                .selectItemFromDropdown(testData.getJsonData("arrivalPort"))
                .enterVesselNumber(testData.getJsonData("vesselNumber"))
                .clickOnManifestViewButton()
                .openTransportationDropdown()
                .searchInDropdown(testData.getJsonData("transportation"))
                .selectItemFromDropdown(testData.getJsonData("transportation"))
                .openCountryDepartureDropdown()
                .searchInDropdown(testData.getJsonData("countryDeparture"))
                .selectItemFromDropdown(testData.getJsonData("countryDeparture"))
                .openPortDepartureDropdown()
                .searchInDropdown(testData.getJsonData("portDeparture"))
                .selectItemFromDropdown(testData.getJsonData("portDeparture"))
                .openExpectedArrivalDatePicker()
                .selectDayFromCalendar(testData.getJsonData("expectedArrivalDay"))
                .confirmDateSelection()
                .confirmDateSelection()
                .openDepartureDatePicker()
                .selectDayFromCalendar(testData.getJsonData("departureDay"))
                .confirmDateSelection()
                .confirmDateSelection()
                .enterCaptainName(testData.getJsonData("captainName"))
                .enterVoyageNumber(testData.getJsonData("voyageNumber"))
                .openMainWarehouseDropdown()
                .searchInDropdown(testData.getJsonData("mainWarehouse"))
                .selectItemFromDropdown(testData.getJsonData("mainWarehouse"))
                .checkHavingPassengers()
                .enterNumberOfPassengers(testData.getJsonData("numberOfPassengers"))
                .enterNotes(testData.getJsonData("notes"))
                .clickOnNextButton()
                .verifyManifestSuccessMessage()
                .getGeneratedManifestNumber();
    }


    //Configurations
    @BeforeClass(alwaysRun = true)
    protected void preCondition() {
        testData = new JsonReader("manifest-data");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quitDriver();
    }
}
