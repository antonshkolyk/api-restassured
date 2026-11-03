package tests;

import clients.PetClient;
import config.ApiConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PetApiTest {
    private PetClient petClient;

    @BeforeEach
    void setUp() {
        petClient = new PetClient(ApiConfig.petstoreBaseUrl());
    }

    @Test
    void CheckCreateAndFetchePetById() {
        Response created = petClient.createPet("Rex", "available");
        created.then().log().all();

        assertEquals(200, created.statusCode());
        String petId = created.jsonPath().getString("id");
        assertNotNull(petId);

        Response fetched = petClient.getPet(petId);
        fetched.then().log().all();

        assertEquals(200, fetched.statusCode());
        assertEquals("Rex", fetched.jsonPath().getString("name"));
        assertEquals("available", fetched.jsonPath().getString("status"));
    }

    @Test
    void CheckCreateAvailablePetResponseMatchesPetSchema() {
        Response response = petClient.createPet("Schema Create", "available");
        response.then().log().all();

        assertEquals(200, response.statusCode());
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/pet-response.schema.json"));
    }

    @Test
    void CheckCreateSoldPetResponseMatchesPetSchema() {
        Response created = petClient.createPet("Schema Fetch", "sold");
        created.then().log().all();

        assertEquals(200, created.statusCode());
        String petId = created.jsonPath().getString("id");
        assertNotNull(petId);

        Response fetched = petClient.getPet(petId);
        fetched.then().log().all();

        assertEquals(200, fetched.statusCode());
        fetched.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/pet-response.schema.json"));
    }
}
