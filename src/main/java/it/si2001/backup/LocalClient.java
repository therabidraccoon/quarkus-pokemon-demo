package it.si2001.backup;

import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@RegisterRestClient
public interface LocalClient {

    @GET
    @Path("/test")
    @Produces("application/json")
    Uni<JsonObject> getTests();
}
