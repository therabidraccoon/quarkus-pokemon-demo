package it.si2001.backup;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@RegisterRestClient
public interface SWClient {

    @GET
    @Path("/planets")
    @Produces("application/json")
    Uni<JsonObject> getPlanets();
}
