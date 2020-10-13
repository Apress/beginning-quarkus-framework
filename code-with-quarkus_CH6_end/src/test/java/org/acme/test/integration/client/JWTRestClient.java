package org.acme.test.integration.client;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RegisterRestClient(baseUri = "http://localhost:8081/hello-world/hello")
@ApplicationScoped
public interface JWTRestClient {

    @GET
    @Path("/{firstName}/{lastName}/generate-token-for")
    @Produces(MediaType.TEXT_PLAIN)
    public String generateJWT(@PathParam("firstName")String firstName, @PathParam("lastName")String lastName);
}
