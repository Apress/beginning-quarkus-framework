package org.acme;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import org.acme.dto.EventMessage;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Path("/hello")
public class EventBusResource {

    final Logger logger = Logger.getLogger(EventBusResource.class.getName());

    @Inject
    EventBus eventBus;

    @POST
    @Path("/send-event")
    @Operation(summary = "Sends a message across to the Vertx event bus")
    @APIResponses({@APIResponse(responseCode = "200")})
    public void sendEvent(@RequestBody(description = "The JSON payload containing message to send", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventMessage.class)))EventMessage message) throws InterruptedException, ExecutionException {
        eventBus.publish("multi-cast", message);
        DeliveryOptions options = new DeliveryOptions();
        options.addHeader("send-time", Instant.now().toString())
                .setLocalOnly(true)
                .setSendTimeout(2000);

        eventBus.request("one-way-message", message, options);
        eventBus.request("", message).on().item().apply(responseMessage -> {
            logger.info("Blocking two-way response received. Address " + responseMessage.address() + ", message: " + responseMessage.body()
            );
            return responseMessage.body();
        });
        eventBus.request("blocking-two-way-message", message).on().item().invoke(responseMessage -> logger.info("Blocking two-way response received. Address " + responseMessage.address() + ", message: " + responseMessage.body()
        ));
        Message<Object> response = eventBus.request("two-way-message", message.getMessage()).subscribe().asCompletionStage().get();
        logger.info("Non-blocking two-way response: " + response.body());
    }

}
