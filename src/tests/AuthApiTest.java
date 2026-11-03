package tests;

import clients.AuthClient;
import config.ApiConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthApiTest {
    private AuthClient authClient;

    @BeforeEach
    void setUp() {
        authClient = new AuthClient(ApiConfig.baseUrl());
    }

    @Test
    void validCredentialsRedirectToSecureAreaAndSetSessionCookie() {
        Response response = authClient.login(ApiConfig.validUsername(), ApiConfig.validPassword());

        assertEquals(303, response.statusCode());
        assertTrue(response.getHeader("Location").contains("/secure"));
        assertTrue(response.getHeader("Set-Cookie").contains("rack.session"));
    }

    @ParameterizedTest
    @CsvSource({
            "wronguser, wrongpassword",
            "tomsmith, wrongpassword",
            "wronguser, SuperSecretPassword!"
    })
    void invalidCredentialsRedirectToLogin(String username, String password) {
        Response response = authClient.login(username, password);

        assertEquals(303, response.statusCode());
        assertTrue(response.getHeader("Location").contains("/login"));
    }
}
