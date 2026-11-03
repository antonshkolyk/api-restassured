package clients;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static io.restassured.config.RestAssuredConfig.config;

public class AuthClient {
    private final String baseUrl;

    public AuthClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public RequestSpecification loginRequest(String username, String password) {
        return with()
                .config(config().redirect(redirectConfig().followRedirects(false)))
                .baseUri(baseUrl)
                .formParam("username", username)
                .formParam("password", password);
    }
}
