package it.si2001.resources;

import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
public class PokemonResource {

    @Inject
    EventBus bus;

    @Route(methods = Route.HttpMethod.GET, path = "/pokemon-batch")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Uni<JsonObject> pkmBatch() {
        return bus.<JsonObject>request("start.pkm.batch", null).onItem().transform(Message::body);
    }

    @Route(methods = Route.HttpMethod.GET, path = "/pokemon-mock")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public String pkmMock() {
        return """
                {
                    "count": 1126,
                    "next": "https://pokeapi.co/api/v2/pokemon?offset=20&limit=20",
                    "previous": null,
                    "results": [
                        {
                            "name": "bulbasaur",
                            "url": "https://pokeapi.co/api/v2/pokemon/1/"
                        },
                        {
                            "name": "ivysaur",
                            "url": "https://pokeapi.co/api/v2/pokemon/2/"
                        },
                        {
                            "name": "venusaur",
                            "url": "https://pokeapi.co/api/v2/pokemon/3/"
                        },
                        {
                            "name": "charmander",
                            "url": "https://pokeapi.co/api/v2/pokemon/4/"
                        },
                        {
                            "name": "charmeleon",
                            "url": "https://pokeapi.co/api/v2/pokemon/5/"
                        },
                        {
                            "name": "charizard",
                            "url": "https://pokeapi.co/api/v2/pokemon/6/"
                        },
                        {
                            "name": "squirtle",
                            "url": "https://pokeapi.co/api/v2/pokemon/7/"
                        },
                        {
                            "name": "wartortle",
                            "url": "https://pokeapi.co/api/v2/pokemon/8/"
                        },
                        {
                            "name": "blastoise",
                            "url": "https://pokeapi.co/api/v2/pokemon/9/"
                        },
                        {
                            "name": "caterpie",
                            "url": "https://pokeapi.co/api/v2/pokemon/10/"
                        },
                        {
                            "name": "metapod",
                            "url": "https://pokeapi.co/api/v2/pokemon/11/"
                        },
                        {
                            "name": "butterfree",
                            "url": "https://pokeapi.co/api/v2/pokemon/12/"
                        },
                        {
                            "name": "weedle",
                            "url": "https://pokeapi.co/api/v2/pokemon/13/"
                        },
                        {
                            "name": "kakuna",
                            "url": "https://pokeapi.co/api/v2/pokemon/14/"
                        },
                        {
                            "name": "beedrill",
                            "url": "https://pokeapi.co/api/v2/pokemon/15/"
                        },
                        {
                            "name": "pidgey",
                            "url": "https://pokeapi.co/api/v2/pokemon/16/"
                        },
                        {
                            "name": "pidgeotto",
                            "url": "https://pokeapi.co/api/v2/pokemon/17/"
                        },
                        {
                            "name": "pidgeot",
                            "url": "https://pokeapi.co/api/v2/pokemon/18/"
                        },
                        {
                            "name": "rattata",
                            "url": "https://pokeapi.co/api/v2/pokemon/19/"
                        },
                        {
                            "name": "raticate",
                            "url": "https://pokeapi.co/api/v2/pokemon/20/"
                        }
                    ]
                }
                """;
    }

}
