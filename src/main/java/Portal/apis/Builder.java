package Portal.apis;


import Portal.utils.dataReader.PropertyReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class Builder {
    private static final String MANIFEST_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJETVhPYWgxdVlEenNGdFdBeHlaSnZvNjVIdkRsNHcxQSIsInN1YiI6IjlkNzg3ODI4LTc4Y2ItNGNiNy05NTg4LTlhNTUyZTc5ZGM2MiIsImlhdCI6MTc4MjQxODIwNC4zODc2NjMsInNjb3BlcyI6W10sImZhY2lsaXR5X2lkIjoiMTUxNTE1MTUzIiwidXNlcl9pZCI6IjlkNzg3ODI4LTc4Y2ItNGNiNy05NTg4LTlhNTUyZTc5ZGM2MiIsImp0aSI6IjdlODdmY2Q3NmUzYmNmYzNiNWM1MmE2NjRhMzY2NTgwMTMzZGI2MzA0NWYy" +
            "NDgzNjg3MjI3MWY2MmZlZjVjZDE0YzNjODVhOTJjNjlkOGUwIiwiYXVkIjoiMSIsIm5iZiI6MTc4MjQxODIwNC4zODc3OTUsImV4cCI6MTgxMzk1NDIwNH0.IHrK9-IutnKxK2Cq1MhyCumGjEsl7epKnTGJF3DSdox6rM0BNNCeHEGFMgU5P-V3grXqC5azdHnuoxcHYiDxu79rbNKpiJ-4AN_jMJI1544KSHGzk4NKOgW7Z3zMxbQ8S6sRjM-gPv-fhvrialzYfwtFxsyLmVeBSK1vtCCH7G43WLAgmNkimRClnSKFh7PlrG-793hXQ-rftWCEmgMHzKfhQS-HgrCNTWWc0Tgbl5QCNF6qaGbA-0CFR3w6ESclLfdjimQkfVmev7vXfJkMd7hxcPkBRny" +
            "142KLJk6aIdZx-aV3PtOKuGwsis2EVzjedjAPQnOOX1R13TSYwu4TqTqmEf5fHEebjPPWNW5aZLkqSIKe5yeA2pfrzkmDqEaCBlr-lnXMDzxEP5d3_9D8SSyOlQmTaIWHrv0Xnf5tDiTMDofePi2--vX0T7TRSwm1HLjBdfDMzQf4ZlHNXen_bQ7Jqd8CLdSQJCzy37nsKTUyi-ysCTFMsIZfkWjQ01bmgvPjNwTg1B3hz4tGnFEXbU4hKfFz7oiDiGrwMMY17TaDWYn1H_zQtJ72riRnlZ1oiyh8usOxnwS4YqcqtJaJ0fO8WZ3onqLkQuB45y6aOYQcEiVpDm0qbktKICJW9lQngdbY2Z4KbL6HNdpgcdrpI81S6UcbshvbSLBPXYJfeuA";
    private static String baseURI = PropertyReader.getProperty("baseUrlApi");

    private Builder() {
        // Private constructor to prevent instantiation
    }

    //build request specification
    public static RequestSpecification getUserManagementRequestSpecification(Map<String, ?> formParams) {
        return new RequestSpecBuilder().setBaseUri(baseURI)
                .setContentType(ContentType.URLENC)
                .addFormParams(formParams)
                .build();
    }

    //build manifest request specification with Bearer token (multipart/form-data)
    public static RequestSpecification getManifestRequestSpecification(Map<String, ?> formParams) {
        RequestSpecification spec = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .addHeader("Authorization", "Bearer " + MANIFEST_TOKEN)
                .addHeader("Accept", "application/json")
                .build();

        for (Map.Entry<String, ?> entry : formParams.entrySet()) {
            spec.multiPart(entry.getKey(), entry.getValue().toString());
        }

        return spec;
    }
}
