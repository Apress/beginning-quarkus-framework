package org.acme.test.integration;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.RestAssured;
import org.acme.test.profile.SampleTestProfile;
import org.junit.jupiter.api.Test;

@TestProfile(SampleTestProfile.class)
@QuarkusTest
public class ProfileTest {

    @Test
    public void validate_that_alternative_resource_is_generated() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured
                .when().get("/hello-world/hello/alternative")
                .then()
                .statusCode(200);
    }

}
