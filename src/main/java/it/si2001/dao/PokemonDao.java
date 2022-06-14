package it.si2001.dao;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import it.si2001.model.Pokemon;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Slf4j
@ApplicationScoped
public class PokemonDao {

    @Inject
    MySQLPool mySQLPool;

    public Uni<Void> insertPokemon(Pokemon pokemon) {
        return mySQLPool.preparedQuery("INSERT INTO pokemons(name,link) VALUES(?,?)")
                .execute(Tuple.of(pokemon.getName(), pokemon.getLink()))
                .replaceWithVoid();
    }

    public Uni<Pokemon> getPokemonByName(String name) {
        return mySQLPool.preparedQuery("SELECT id,name,link FROM pokemons WHERE name = ?")
                .execute(Tuple.of(name))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(iterator -> {
                    if(iterator.hasNext()) {
                        Row row = iterator.next();
                        return Pokemon.builder()
                                .id(row.getLong("id"))
                                .name(row.getString("name"))
                                .link(row.getString("link"))
                                .build();
                    } else {
                        return null;
                    }
                });
    }
}
