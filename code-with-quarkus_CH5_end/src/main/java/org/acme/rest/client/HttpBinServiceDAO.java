package org.acme.rest.client;


import org.acme.dto.HttpBinAnythingResponse;
import org.acme.filter.LoggingFilter;
import org.acme.provider.exception.HttpBinExceptionMapper;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Bulkhead;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

@RegisterRestClient(baseUri = "https://httpbin.org/")
@RegisterProvider(LoggingFilter.class)
@RegisterProvider(HttpBinExceptionMapper.class)
@ClientHeaderParam(name="Authorization",value = "{computeBasicAuthHeader}")
public interface HttpBinServiceDAO {

    final Logger LOGGER = Logger.getLogger(HttpBinServiceDAO.class.getName());

    @GET
    @Path("/image/jpeg")
    @Produces("image/jpeg")
    @Retry(delay = 2000,delayUnit = ChronoUnit.MILLIS,maxRetries = 4, retryOn = {SocketTimeoutException.class, ConnectException.class})
    @Asynchronous
    @Bulkhead(value = 10,waitingTaskQueue = 30)
    CompletionStage<byte[]> getImageAsync();

    @PATCH
    @Path("/delay/{delay}")
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout(value = 5000,unit = ChronoUnit.MILLIS)
    Response patchRandom(@PathParam("delay") int delay) throws Exception;

    @POST
    @Path("/anything/{anything}")
    @Produces(MediaType.APPLICATION_JSON)
    HttpBinAnythingResponse postAnything(@PathParam("anything") String anything);

    default String computeBasicAuthHeader(){
        LOGGER.info("Computing basic auth header ");
        return "Basic " + Base64.getEncoder().encodeToString(
                "username:password".getBytes());
    }
}



