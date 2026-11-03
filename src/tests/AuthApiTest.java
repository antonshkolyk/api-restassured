package tests;

import clients.AuthClient;
import config.ApiConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.Matchers.containsString;

class AuthApiTest {
    private AuthClient authClient;

    @BeforeEach
    void setUp() {
        authClient = new AuthClient(ApiConfig.baseUrl());
    }

    @Test
    void validCredentialsRedirectToSecureAreaAndSetSessionCookie() {
        authClient.loginRequest(ApiConfig.validUsername(), ApiConfig.validPassword())
                .when()
                .post("/authenticate")
                .then()
                .statusCode(303)
                .header("Location", containsString("/secure"))
                .header("Set-Cookie", containsString("rack.session"));
    }

    @ParameterizedTest
    @CsvSource({
            "wronguser, wrongpassword",
            "tomsmith, wrongpassword",
            "wronguser, SuperSecretPassword!"
    })
    void invalidCredentialsRedirectToLogin(String username, String password) {
        authClient.loginRequest(username, password)
                .when()
                .post("/authenticate")
                .then()
                .statusCode(303)
                .header("Location", containsString("/login"));
    }
}
