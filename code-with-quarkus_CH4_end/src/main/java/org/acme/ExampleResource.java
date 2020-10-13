package org.acme;

import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;
import io.smallrye.mutiny.Uni;
import org.acme.dto.AnagramRequest;
import org.acme.dto.AnagramResponse;
import org.acme.rest.client.HttpBinServiceDAO;
import org.acme.util.Scrambler;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.Cache;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;


@Path("/hello")
public class ExampleResource {

    @Inject
    ManagedExecutor managedExecutor;

    @Inject
    @ConfigProperty(name = "quarkus.application.name", defaultValue = "my-app-name")
    String applicationName;

    @Inject
    @ConfigProperty(name="my.app.list.of.admins")
    Set<String> admins;

    @Inject
    ServletContext servletContext;

    final Logger logger = Logger.getLogger(ExampleResource.class.getName());

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Cache(maxAge = 30)
    public String hello() {
        return "hello from: " + applicationName;
    }

    @Path("/{name:[a-zA-Z]*}/scramble")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @CacheResult(cacheName = "anagramCache")
    @Operation(summary = "scrambles the supplied username asynchronously", description = "scrambles the name in the path. There are no guarantees of uniqueness")
    @APIResponses({@APIResponse(name = "Conversion Response", responseCode = "200", description = "the scrambled name. no uniqueness guarantees")})
    public AnagramResponse getAnagram(@PathParam("name") final String nameToScramble) {
        logger.info("Generating anagram of: " + nameToScramble);
        AnagramResponse response = generateAnagram(nameToScramble);
        return response;
    }

    @POST
    @Path("/scramble")
    @Produces(MediaType.APPLICATION_JSON)
    @CacheResult(cacheName = "anagramCache")
    @Operation(summary = "scrambles and saves the text supplied in the body asynchronously", description = "scrambles the name in the path. There are no guarantees of uniqueness")
    @APIResponses({@APIResponse(name = "Conversion Response", responseCode = "200", description = "the scrambled name. no uniqueness guarantees")})
    public AnagramResponse persistAnagram(@RequestBody(description = "The JSON payload containing text to scramble", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnagramRequest.class))) AnagramRequest anagramRequest) {
        AnagramResponse response = generateAndSaveAnagram(anagramRequest);
        return response;
    }

    @GET
    @Path("/{name:[a-zA-Z]*}/scramble-async")
    @RolesAllowed("VIP")
    public void getAnagramAsync(@Suspended final AsyncResponse async, final @PathParam("name") String name) {
        final Future<?> toComplete = managedExecutor.submit(() -> {
            logger.info("Executing in a different thread");
            final Response response = Response.ok(generateAnagram(name))
                    .type(MediaType.APPLICATION_JSON).
                            build();
            async.resume(response);
        });
    }

    @GET
    @Path("/{name:[a-zA-Z]*}/scramble-rx")
    public CompletionStage<Response> getAnagramRx(final @PathParam("name") String name) {
        final CompletionStage<Response>
                completionStageResponse = new CompletableFuture<>();
        managedExecutor.submit(() -> {
            logger.info("Executing in different thread");
            final Response response = Response.ok(getAnagram(name))
                    .type(MediaType.APPLICATION_JSON).
                            build();

            ((CompletableFuture) completionStageResponse).complete(response);
        });
        return completionStageResponse;
    }

    @GET
    @Path("/{name:[a-zA-Z]*}/scramble-mutiny")
    public Uni<Response> getMutinyAnagram(final @PathParam("name") String textToScramble) {
        return Uni.createFrom().item(textToScramble).onItem().
                apply(toScramble -> {
                    return Response.ok(getAnagram(toScramble)).
                            type(MediaType.APPLICATION_JSON).build();
                });
    }

    @Path("/{name:[a-zA-Z]*}/clear-anagram-cache")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @CacheInvalidate(cacheName = "anagramCache")
    public void clearAnagramCache(@PathParam("name") final String cacheKey) {
        logger.info("Removing " + cacheKey + " from anagram cache");
    }

    @Path("/{name:[a-zA-Z]*}/clear-all-cache")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @CacheInvalidateAll(cacheName = "anagramCache")
    public void clearAllAnagramCache() {

    }

    AnagramResponse generateAnagram(String original) {
        AnagramResponse anagramResponse = new AnagramResponse();
        String anagram = Scrambler.scramble(original);
        anagramResponse.setAnagram(anagram);
        return anagramResponse;
    }

    AnagramResponse generateAndSaveAnagram(AnagramRequest original) {
        AnagramResponse anagramResponse = generateAnagram(original.getSourceText());
        //TODO: database persistence code
        return anagramResponse;
    }

}