package org.acme.test.unit;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.acme.dto.HttpBinAnythingResponse;
import org.acme.rest.client.HttpBinServiceDAO;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

@QuarkusTest
@DisplayName("Unit test the Microprofile RESTClient")
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class RestClientTest {

    @InjectMock
    @RestClient
    HttpBinServiceDAO binServiceRestClient;

    HttpBinAnythingResponse binResponse;

    @BeforeAll
    public void init(){
        binResponse = new HttpBinAnythingResponse();
        binResponse.setResponse("Hi, Drake!");
        Mockito.when(binServiceRestClient.postAnything("Say something")).thenReturn(binResponse);
    }

    @Test
    @DisplayName("Test mocking of REST Client")
    public void testRestClient(){
        HttpBinAnythingResponse response = binServiceRestClient.postAnything("Say something");
        Assertions.assertEquals(binResponse,response);
    }
}
