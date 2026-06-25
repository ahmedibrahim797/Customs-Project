package portal.Tests;


import Portal.apis.ManifestAPI;
import Portal.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Customs Project")
@Feature("Customs Services")
@Story("Create Manifest API")
@Severity(SeverityLevel.CRITICAL)
@Owner("Elnems")
public class CreateManifestAPITest {

    private JsonReader testData;

    @Description("Create a new manifest via API with valid data")
    @Test(groups = "manifestAPI")
    public void createManifestAPITC() {
        new ManifestAPI()
                .createManifest(
                        testData.getJsonData("facility_created_for"),
                        testData.getJsonData("has_secondary_agents"),
                        testData.getJsonData("has_empty_containers"),
                        testData.getJsonData("has_passengers"),
                        testData.getJsonData("apa_vessel_visit_id"),
                        testData.getJsonData("transport_means_type"),
                        testData.getJsonData("manifest_type_id"),
                        testData.getJsonData("berth_no"),
                        testData.getJsonData("estimated_arrival_date"),
                        testData.getJsonData("actual_departure_timestamp"),
                        testData.getJsonData("transport_mean_arrival_timestamp"),
                        testData.getJsonData("arrival_port"),
                        testData.getJsonData("departure_port"),
                        testData.getJsonData("captain_name"),
                        testData.getJsonData("vessel_country"),
                        testData.getJsonData("passenger_count"),
                        testData.getJsonData("main_warehouse"),
                        testData.getJsonData("notes"),
                        testData.getJsonData("voyage_number")
                )
                .verifyManifestCreatedSuccessfully();
    }

    //Configurations
    @BeforeClass(alwaysRun = true)
    protected void preCondition() {
        testData = new JsonReader("create-manifest-api-data");
    }
}
