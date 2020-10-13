package org.acme.test.integration;


import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class SecurityTest {


    @Test
    @TestSecurity(authorizationEnabled = false)
    void testSecurityDisabledGet() {
        RestAssured
                .when().get("/hello-world/hello/{username}/scramble-async","tayo")
                       .then()
                       .statusCode(200);
    }

    @Test
    @TestSecurity
    void testSecurityEnabledGet() {
        RestAssured
                .when().get("/hello-world/hello/{username}/scramble-async","tayo")
                       .then()
                       .statusCode(401);
    }

    @Test
    @TestSecurity(user = "tayo-1", roles = "VIP")
    void testGetWithUserAndRole() {
        RestAssured
                .when().get("/hello-world/hello/{username}/scramble-async","tayo")
                       .then()
                       .statusCode(200);
    }
}
