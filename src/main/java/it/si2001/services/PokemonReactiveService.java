package it.si2001.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import it.si2001.client.PokemonClient;
import it.si2001.backup.TestModel;
import it.si2001.client.response.PokemonDto;
import it.si2001.client.response.PokemonResponseDto;
import it.si2001.dao.PokemonDao;
import it.si2001.model.Pokemon;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ApplicationScoped
public class PokemonReactiveService {

    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Inject
    PokemonDao pokemonDao;

    @ConsumeEvent("start.pkm.batch")
    public Uni<JsonObject> startPkmBatch(String text) {
        log.info("Batch started..");

        PokemonClient pkmClient = RestClientBuilder.newBuilder().baseUri(URI.create("http://localhost:8080")).build(PokemonClient.class);
        return pkmClient.getPokemons().onItem().call(response -> {

            PokemonResponseDto pokemonResponseDto = null;
            try {
                pokemonResponseDto = OBJECT_MAPPER.readValue(response.toString(), new TypeReference<PokemonResponseDto>() {
                });
                return Multi.createFrom().iterable(pokemonResponseDto.getResults())
                        .onItem()
                        .transformToUniAndMerge(pkmDto -> {
                            log.info(pkmDto.getName());
                            return pokemonDao.insertPokemon(Pokemon.builder()
                                    .name(pkmDto.getName())
                                    .link(pkmDto.getUrl())
                                    .build())
                                    .onItem()
                                    .call(() -> pokemonDao.getPokemonByName(pkmDto.getName())
                                            .onItem().call(pkmModel -> {
                                                log.info("log pokemon id: " + pkmModel.getId());
                                                return Uni.createFrom().item(pkmModel);
                                            }));
                        }).collect().asList();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
