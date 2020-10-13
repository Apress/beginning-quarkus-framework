package org.acme.test.integration;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.acme.test.integration.client.JWTRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.inject.Inject;


@QuarkusTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class RestEndpointTest {

    @Inject
    @RestClient
    JWTRestClient jwtRestClient;

    @BeforeAll
    public void init(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

    }

    @ParameterizedTest
    @CsvSource({
            "Olamide, Koleoso",
            "Eden, Wassiamal"
    })
    public void validate_that_valid_jwt_is_generated(String firstName, String lastName) {
        RestAssured
                .when().get("/hello-world/hello/{firstName}/{lastName}/generate-token-for",firstName,lastName)
                .then()
                .statusCode(200);
    }

    @ParameterizedTest
    @CsvSource({
            "Olamide, Koleoso",
            "Eden, Wassiamal"
    })
    public void validate_rest_client_integration(String firstName, String lastName){
       String result = jwtRestClient.generateJWT(firstName,lastName);
       Assertions.assertNotNull(result);
       Assertions.assertNotEquals(result,"");
    }

}
