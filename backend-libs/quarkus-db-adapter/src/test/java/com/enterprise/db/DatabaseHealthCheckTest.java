package com.enterprise.db;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class DatabaseHealthCheckTest {

    @Test
    void livenessCheck_returnsUp() {
        given()
            .when().get("/q/health/live")
            .then()
            .statusCode(200)
            .body("status", is("UP"))
            .body("checks.name", hasItem("database"));
    }

    @Test
    void readinessCheck_returnsUp() {
        given()
            .when().get("/q/health/ready")
            .then()
            .statusCode(200)
            .body("status", is("UP"));
    }
}
