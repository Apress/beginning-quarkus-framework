package org.acme.test.integration;

import javax.enterprise.inject.Alternative;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.ExampleResource;

import io.quarkus.arc.AlternativePriority;

@AlternativePriority(1)
@Path("/hello")
public class MockSampleResource extends ExampleResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("alternative")
    public String hello() {
        return "hello from the alternative resource!";
    }

}


