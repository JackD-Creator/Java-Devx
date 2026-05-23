package com.enterprise.encryption;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class AesEncryptionServiceTest {

    @Test
    void encrypt_thenDecrypt_returnsOriginal() {
        String payload = "{\"value\": \"Hello Enterprise!\"}";

        String encrypted = given()
            .contentType(ContentType.JSON).body(payload)
            .when().post("/api/encryption/aes/encrypt")
            .then().statusCode(200)
            .body("success", is(true))
            .extract().path("result");

        given()
            .contentType(ContentType.JSON)
            .body("{\"value\": \"" + encrypted + "\"}")
            .when().post("/api/encryption/aes/decrypt")
            .then().statusCode(200)
            .body("result", is("Hello Enterprise!"));
    }

    @Test
    void hashPassword_returnsHash() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"value\": \"mySecretPassword\"}")
            .when().post("/api/encryption/hash/password")
            .then().statusCode(200)
            .body("result", startsWith("$2a$"));
    }

    @Test
    void sha256_returnsDeterministicHash() {
        given()
            .contentType(ContentType.JSON)
            .body("{\"value\": \"test\"}")
            .when().post("/api/encryption/hash/sha256")
            .then().statusCode(200)
            .body("result", is("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08"));
    }
}
