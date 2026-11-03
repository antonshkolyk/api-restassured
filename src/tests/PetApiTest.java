package tests;

import clients.PetClient;
import config.ApiConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

class PetApiTest {
    private PetClient petClient;

    @BeforeEach
    void setUp() {
        petClient = new PetClient(ApiConfig.petstoreBaseUrl());
    }

    @Test
    void createdPetCanBeFetchedById() {
        Long petId = petClient.createPetRequest("Rex", "available")
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo("Rex"), "status", equalTo("available"))
                .extract()
                .path("id");

        petClient.getPetRequest(petId)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Rex"), "status", equalTo("available"));
    }

    @Test
    void createdAvailablePetResponseMatchesPetSchema() {
        petClient.createPetRequest("Schema Create", "available")
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/pet-response.schema.json"));
    }

    @Test
    void fetchedSoldPetResponseMatchesPetSchema() {
        Long petId = petClient.createPetRequest("Schema Fetch", "sold")
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .extract()
                .path("id");

        petClient.getPetRequest(petId)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/pet-response.schema.json"));
    }
}
