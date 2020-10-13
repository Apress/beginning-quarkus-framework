package org.acme;

import org.acme.rest.client.HttpBinServiceDAO;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.Cache;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;


@Path("/hello")
public class MicroprofileResource {

    final Logger logger = Logger.getLogger(MicroprofileResource.class.getName());

    @Inject
    @RestClient
    HttpBinServiceDAO httpBinService;

    @GET
    @Path("/hello-image")
    @Cache(maxAge = 30)
    @Operation(summary = "Returns an image")
    @Produces("image/jpg")
    @RolesAllowed("VIP")
    public Response helloImage() throws InterruptedException, ExecutionException {
        logger.info("Saying hello image");
        CompletionStage<byte[]> futureImage = httpBinService.getImageAsync();
        byte[] imageBytes = futureImage.toCompletableFuture().get();
        return Response.ok().entity(new StreamingOutput() {
            @Override
            public void write(OutputStream output)
                    throws IOException, WebApplicationException {
                output.write(imageBytes);
                output.flush();
            }
        }).build();
    }
}
