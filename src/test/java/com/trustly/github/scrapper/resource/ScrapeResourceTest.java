package com.trustly.github.scrapper.resource;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ScrapeResourceTest {


    @Test
    void shouldScrapeTestGithubRepository() {

        String expectedJson = "[{\"extension\":\"md\",\"count\":1,\"lines\":1,\"bytes\":22},{\"extension\":\"gitignore\",\"count\":1,\"lines\":23,\"bytes\":278},{\"extension\":\"OTHERS\",\"count\":1,\"lines\":201,\"bytes\":11366},{\"extension\":\"sql\",\"count\":1,\"lines\":2,\"bytes\":296},{\"extension\":\"xml\",\"count\":1,\"lines\":151,\"bytes\":5273}]";


        String body = given().when()
                .queryParam("uri", "https://github.com/leonardogolfeto/github-scrapper-test")
                .get("/scrape")
                .then()
                .statusCode(200)
                .extract().asString();


        assertThat(body).isEqualTo(expectedJson);

    }
}