package it.si2001.backup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import it.si2001.backup.MockContract;
import it.si2001.backup.MockRequest;
import it.si2001.backup.TestModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class MockResource {

    @Inject
    EventBus bus;

    @Route(methods = Route.HttpMethod.GET, path = "/hello")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Uni<JsonObject> hello() {
        return bus.<JsonObject>request("start.batch", "test").onItem().transform(Message::body);
    }

    @Route(methods = Route.HttpMethod.GET, path = "/test")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Uni<List<TestModel>> test() {
        return Uni.createFrom().item(Arrays.asList(
                TestModel.builder().title("primo").build()
                , TestModel.builder().title("secondo").build()
                , TestModel.builder().title("terzo").build()));
    }


    @Route(methods = Route.HttpMethod.POST, path = "/contracts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Response searchContracts(@Body MockRequest request) {
        List<MockContract> mockContractList = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            mockContractList = objectMapper.readValue(DATA, new TypeReference<List<MockContract>>() {
            });
            if (request != null) {
                mockContractList = mockContractList.stream()
                        .filter(mockContract -> {
                            boolean valid = false;
                            if (request.getCustomer() != null && mockContract.getFullName().equals(request.getCustomer())) {
                                valid = true;
                            }
                            return valid;
                        }).collect(Collectors.toList());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return Response.ok(mockContractList).build();
    }

    private final String DATA = """
            [
                 {
                     "id": "37884",
                     "full_name": "Test Mario",
                     "type_contract": "CONSUMO",
                     "date_signing": "2016-07-14",
                     "expiration_date": "2025-04-23",
                     "acquired_packages": "57",
                     "consumed_packages": "15",
                     "residual_packages": "44"
                 },
                 {
                     "id": "95988",
                     "full_name": "Test Fermin",
                     "type_contract": "CONSUMO",
                     "date_signing": "2012-11-20",
                     "expiration_date": "2022-05-14",
                     "acquired_packages": "62",
                     "consumed_packages": "17",
                     "residual_packages": "28"
                 },
                 {
                     "id": "32038",
                     "full_name": "Test Aryanna",
                     "type_contract": "CONSUMO",
                     "date_signing": "2019-06-22",
                     "expiration_date": "2028-03-05",
                     "acquired_packages": "70",
                     "consumed_packages": "41",
                     "residual_packages": "39"
                 },
                 {
                     "id": "77976",
                     "full_name": "Test Dean",
                     "type_contract": "CONSUMO",
                     "date_signing": "2012-12-02",
                     "expiration_date": "2024-01-14",
                     "acquired_packages": "72",
                     "consumed_packages": "20",
                     "residual_packages": "30"
                 },
                 {
                     "id": "39902",
                     "full_name": "Test Letha",
                     "type_contract": "CONSUMO",
                     "date_signing": "2016-07-03",
                     "expiration_date": "2022-08-06",
                     "acquired_packages": "82",
                     "consumed_packages": "4",
                     "residual_packages": "10"
                 },
                 {
                     "id": "42490",
                     "full_name": "Test Kara",
                     "type_contract": "CONSUMO",
                     "date_signing": "2011-07-18",
                     "expiration_date": "2028-05-22",
                     "acquired_packages": "100",
                     "consumed_packages": "27",
                     "residual_packages": "15"
                 },
                 {
                     "id": "42727",
                     "full_name": "Test Nella",
                     "type_contract": "CONSUMO",
                     "date_signing": "2013-09-26",
                     "expiration_date": "2023-09-19",
                     "acquired_packages": "95",
                     "consumed_packages": "5",
                     "residual_packages": "32"
                 },
                 {
                     "id": "15290",
                     "full_name": "Test Astrid",
                     "type_contract": "CONSUMO",
                     "date_signing": "2012-04-28",
                     "expiration_date": "2026-09-21",
                     "acquired_packages": "51",
                     "consumed_packages": "16",
                     "residual_packages": "19"
                 },
                 {
                     "id": "64038",
                     "full_name": "Test Helen",
                     "type_contract": "CONSUMO",
                     "date_signing": "2021-03-25",
                     "expiration_date": "2029-03-25",
                     "acquired_packages": "91",
                     "consumed_packages": "33",
                     "residual_packages": "26"
                 },
                 {
                     "id": "81177",
                     "full_name": "Test Kane",
                     "type_contract": "CONSUMO",
                     "date_signing": "2021-07-29",
                     "expiration_date": "2023-08-19",
                     "acquired_packages": "76",
                     "consumed_packages": "38",
                     "residual_packages": "41"
                 }
             ]
            """;

}