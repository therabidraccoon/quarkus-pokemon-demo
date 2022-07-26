package it.si2001.resources;

import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
public class SecuredResource {

    @Inject
    EventBus bus;

    @Inject
    JsonWebToken jwt;

    @Route(methods = Route.HttpMethod.GET, path = "/test-permit-all")
    @PermitAll
    @Produces({MediaType.APPLICATION_JSON})
    public String pkmMock() {
        return jwt.toString();
    }

}
