package Portal.apis;


import Portal.utils.logs.LogsManager;
import Portal.validation.Verification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class ManifestAPI {
    //endpoints
    private static final String createManifest_endpoint = "/manifests";
    RequestSpecification requestSpecification;
    Response response;
    Verification verification;

    public ManifestAPI() {
        // Constructor
        requestSpecification = RestAssured.given();
        verification = new Verification();
    }

    //api methods
    @Step("Create a new manifest with full details")
    public ManifestAPI createManifest(String facilityCreatedFor, String hasSecondaryAgents, String hasEmptyContainers,
                                      String hasPassengers, String apaVesselVisitId, String transportMeansType,
                                      String manifestTypeId, String berthNo, String estimatedArrivalDate,
                                      String actualDepartureTimestamp, String transportMeanArrivalTimestamp,
                                      String arrivalPort, String departurePort, String captainName,
                                      String vesselCountry, String passengerCount, String mainWarehouse,
                                      String notes, String voyageNumber) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("facility_created_for", facilityCreatedFor);
        formParams.put("has_secondary_agents", hasSecondaryAgents);
        formParams.put("has_empty_containers", hasEmptyContainers);
        formParams.put("has_passengers", hasPassengers);
        formParams.put("apa_vessel_visit_id", apaVesselVisitId);
        formParams.put("transport_means_type", transportMeansType);
        formParams.put("manifest_type_id", manifestTypeId);
        formParams.put("berth_no", berthNo);
        formParams.put("estimated_arrival_date", estimatedArrivalDate);
        formParams.put("actual_departure_timestamp", actualDepartureTimestamp);
        formParams.put("transport_mean_arrival_timestamp", transportMeanArrivalTimestamp);
        formParams.put("arrival_port", arrivalPort);
        formParams.put("departure_port", departurePort);
        formParams.put("captain_name", captainName);
        formParams.put("vessel_country", vesselCountry);
        formParams.put("passenger_count", passengerCount);
        formParams.put("main_warehouse", mainWarehouse);
        formParams.put("notes", notes);
        formParams.put("voyage_number", voyageNumber);

        response = requestSpecification.spec(Builder.getManifestRequestSpecification(formParams))
                .post(createManifest_endpoint);
        LogsManager.info(response.asPrettyString());
        return this;
    }

    //validations
    @Step("Verify that manifest is created successfully")
    public ManifestAPI verifyManifestCreatedSuccessfully() {
        verification.assertTrue(response.getStatusCode() == 200 || response.getStatusCode() == 201,
                "Manifest is not created successfully, status code: " + response.getStatusCode());
        return this;
    }

    @Step("Verify response status code")
    public ManifestAPI verifyStatusCode(int expectedStatusCode) {
        verification.Equals(String.valueOf(response.getStatusCode()), String.valueOf(expectedStatusCode),
                "Status code mismatch");
        return this;
    }

    @Step("Verify duplicate manifest error response")
    public ManifestAPI verifyDuplicateManifestError() {

        // 1. استخراج القيم الفعلية من الـ Response
        String actualStatusCode = String.valueOf(response.getStatusCode());
        String actualMessage = response.jsonPath().getString("message"); // استخدم getString لتجنب أخطاء الـ Casting

        // 2. تعريف الحالة الأولى (القديمة)
        boolean isFirstScenario = actualStatusCode.equals("422") &&
                "The apa vessel visit id has already been taken.".equals(actualMessage);

        // 3. تعريف الحالة الثانية (من الصورة: HTTP Status 200 أو 500 مع الرسالة الجديدة)
        boolean isSecondScenario = (actualStatusCode.equals("200") || actualStatusCode.equals("500")) &&
                "Failed to create manifest: Manifest with the same vessel id, port, and manifest type already exists".equals(actualMessage);

        // 4. التحقق (Assertion) من أن إحدى الحالتين على الأقل (True)
        verification.assertTrue(
                isFirstScenario || isSecondScenario,
                "Failed! Unexpected duplicate manifest response. \nActual Status: " + actualStatusCode +
                        "\nActual Message: " + actualMessage
        );

        return this;
    }

    public Response getResponse() {
        return response;
    }
}
