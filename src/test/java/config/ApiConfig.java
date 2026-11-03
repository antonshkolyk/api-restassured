package config;

public final class ApiConfig {
    private ApiConfig() {
    }

    public static String baseUrl() {
        return env("BASE_URL", "https://the-internet.herokuapp.com");
    }

    public static String petstoreBaseUrl() {
        return env("PETSTORE_BASE_URL", "https://petstore.swagger.io/v2");
    }

    public static String validUsername() {
        return env("VALID_USERNAME", "tomsmith");
    }

    public static String validPassword() {
        return env("VALID_PASSWORD", "SuperSecretPassword!");
    }

    private static String env(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
