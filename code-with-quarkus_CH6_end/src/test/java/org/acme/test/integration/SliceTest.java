package org.acme.test.integration;

import io.quarkus.test.QuarkusUnitTest;
import io.restassured.RestAssured;
import org.acme.ExampleResource;
import org.acme.dao.vertx.EventBusMessageRecipient;
import org.acme.java.lambda.LambdaHandlerImpl;
import org.acme.test.integration.client.JWTRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SliceTest {

    @Inject
    @RestClient
    JWTRestClient jwtRestClient;

    @RegisterExtension
    static QuarkusUnitTest runner = new QuarkusUnitTest()
            .setArchiveProducer(() -> ShrinkWrap.create(JavaArchive.class)
            .addClasses(ExampleResource.class, EventBusMessageRecipient.class,JWTRestClient.class, LambdaHandlerImpl.class));
        
    @ParameterizedTest
    @CsvSource({
            "Olamide, Koleoso",
            "Eden, Wassiamal"
    })
    public void validate_rest_client_throws_404_for_undeployed_resource(String firstName, String lastName){
        WebApplicationException exception = Assertions.assertThrows(WebApplicationException.class, ()-> jwtRestClient.generateJWT(firstName,lastName));

    }

    @Test
    public void validate_rest_assured_gets_200_for_deployed_endpoint(){
        RestAssured
                .when().get("/hello-world/hello")
                .then()
                .statusCode(200);
    }
}
