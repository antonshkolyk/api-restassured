package clients;

import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.with;

public class PetClient {
    private final String baseUrl;

    public PetClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public RequestSpecification createPetRequest(String name, String status) {
        Map<String, Object> pet = Map.of(
                "name", name,
                "status", status,
                "photoUrls", new String[0]);

        return with()
                .log().all()
                .baseUri(baseUrl)
                .contentType("application/json")
                .body(pet);
    }

    public RequestSpecification getPetRequest(String petId) {
        return with()
                .log().all()
                .baseUri(baseUrl)
                .pathParam("petId", petId);
    }
}
