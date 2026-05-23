package com.enterprise.theme;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class ThemeResourceTest {

    @Test
    void listAllThemes_returnsOk() {
        given()
            .when().get("/api/themes")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("$", not(empty()));
    }

    @Test
    void getActiveTheme_returnsOk() {
        given()
            .when().get("/api/themes/active")
            .then()
            .statusCode(200)
            .body("active", is(true))
            .body("slug", notNullValue());
    }

    @Test
    void createTheme_returnsCreated() {
        String payload = """
            {
              "name": "Test Theme",
              "slug": "test-theme",
              "active": false,
              "mode": "light",
              "tokensJson": "{}"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(payload)
            .when().post("/api/themes")
            .then()
            .statusCode(201)
            .body("slug", is("test-theme"));
    }
}
