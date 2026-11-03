# api-restassured

An API test automation project inspired by the sibling TypeScript API test examples. It uses Java, JUnit, and RestAssured to exercise authentication and Petstore APIs without browser/UI dependencies.

## Stack

| Layer | Tool |
|---|---|
| Language | Java 17 |
| API testing | RestAssured 5 |
| Test runner | JUnit Jupiter 5 |
| Contract validation | RestAssured JSON Schema Validator |
| Build | Maven |

## Project structure

```text
src/test/
  java/com/example/apitests/
    AuthApiTest.java              # Login API tests
    PetApiTest.java               # Petstore create/fetch/schema tests
    clients/
      AuthClient.java             # Login request
      PetClient.java              # Pet create and fetch requests
    config/ApiConfig.java         # Environment-driven base URLs and credentials
  resources/schemas/
    pet-response.schema.json
```

## Prerequisites and setup

- JDK 17+
- Maven 3.8+

```powershell
# Run from the project directory
mvn test
```

Maven downloads the dependencies on the first run. JUnit XML reports are written under `target/surefire-reports/`.

## Running tests

```powershell
# All API scenarios
mvn test

# Run one test class
mvn test "-Dtest=PetApiTest"
```

## Configuration

| Environment variable | Default | Description |
|---|---|---|
| `BASE_URL` | `https://the-internet.herokuapp.com` | Authentication API base URL |
| `PETSTORE_BASE_URL` | `https://petstore.swagger.io/v2` | Petstore API base URL |
| `VALID_USERNAME` | `tomsmith` | Valid demo login username |
| `VALID_PASSWORD` | `SuperSecretPassword!` | Valid demo login password |

Set environment variables in PowerShell before running Maven, for example:

```powershell
$env:PETSTORE_BASE_URL = "https://petstore.swagger.io/v2"
mvn test
```

## Coverage

- Login: valid and invalid credentials, redirects, and session cookie.
- Petstore: create and fetch pets, assert response fields, and validate responses against a JSON Schema.

The Petstore tests use the public Swagger Petstore demo API and require network access.

## CI

GitHub Actions runs the JUnit test suite on pushes and pull requests using Temurin JDK 17. Test reports are uploaded as workflow artifacts.
