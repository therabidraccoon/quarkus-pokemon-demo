package it.si2001.backup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import it.si2001.backup.LocalClient;
import it.si2001.backup.TestModel;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.enterprise.context.ApplicationScoped;
import java.net.URI;
import java.util.List;

@Slf4j
@ApplicationScoped
public class TestReactiveService {

    @ConsumeEvent("start.batch")
    public Uni<JsonObject> startBatch(String text) {
        log.info("Batch started..");
//        SWClient swClient = RestClientBuilder.newBuilder().baseUri(URI.create("https://swapi.dev/api")).build(SWClient.class);
//
//
//        swClient.getPlanets().onItem().invoke(response -> {
//            log.info("called planets:" + response.toString());
//        });

        LocalClient localClient = RestClientBuilder.newBuilder().baseUri(URI.create("http://localhost:8080")).build(LocalClient.class);
        return localClient.getTests().onItem().call(response -> {
            ObjectMapper objectMapper = new ObjectMapper();
            List<TestModel> testList = null;
            try {
                testList = objectMapper.readValue(response.toString(), new TypeReference<List<TestModel>>() {
                });
                testList.stream().forEach(testModel -> log.info(testModel.getTitle()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return Uni.createFrom().item(testList);
        });

//        List<String> responses = localResp.onItem().transform(jsonObject -> {
//            ObjectMapper objectMapper = new ObjectMapper();
//            List<String> testList = null;
//            try {
//                testList = objectMapper.readValue(jsonObject.toString(), new TypeReference<List<String>>() {
//                });
//                testList.stream().forEach(text -> log.info(text));
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//            return testList;
//        });

//        return Uni.createFrom().item(localResp.toString());
    }
}
