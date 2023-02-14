package by.kovzov.uis.specialization.rest.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static java.text.MessageFormat.format;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import by.kovzov.uis.specialization.repository.api.DisciplineRepository;
import by.kovzov.uis.specialization.repository.entity.Discipline;
import by.kovzov.uis.specialization.rest.common.AbstractIntegrationTest;
import io.restassured.http.ContentType;
import lombok.Setter;

@Setter
class DisciplineControllerIT extends AbstractIntegrationTest {

    private static final String BASE_URL = "/api/disciplines/";

    @Autowired
    private DisciplineRepository disciplineRepository;

    private List<Discipline> disciplines;

    @BeforeEach
    void setUp() {
        disciplines = dataLoader.loadJson(Discipline.class, "data/json/disciplines.json");
        disciplineRepository.saveAll(disciplines);
    }

    @Test
    void getByIdReturnValidResponse() {
        int id = 1;

        given()
            .contentType(ContentType.JSON)
            .when()
            .get(BASE_URL + id)
            .then()
            .statusCode(200)
            .body("id", is(id))
            .body("name", is("Spanish"))
            .body("shortName", is("sp"));
    }

    @Test
    void getByIdReturnNotFoundWhenIdNotExist() {
        int id = 999999;

        given()
            .contentType(ContentType.JSON)
            .when()
            .get(BASE_URL + id)
            .then()
            .statusCode(404)
            .body("message", is(format("Discipline with id = {0} not found.", id)))
            .body("path", is(BASE_URL + id));
    }

    @Test
    void getByIdReturnValidJsonSchema() {
        int id = 1;

        given()
            .contentType(ContentType.JSON)
            .when()
            .get(BASE_URL + id)
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schema/discipline.json"));
    }

    @ParameterizedTest
    @CsvSource({
        "'',    4",
        "P,     2",
        "' ',   2",
        "MT,    1",
    })
    void searchReturnContentCountAccordingToQuery(String query, int expectedSize) {
        given()
            .contentType(ContentType.JSON)
            .queryParam("query", query)
            .when()
            .get(BASE_URL + "search")
            .then()
            .statusCode(200)
            .body("content", hasSize(expectedSize))
            .body("totalElements", is(expectedSize));
    }

    @Test
    void searchReturnValidJsonSchema() {
        given()
            .contentType(ContentType.JSON)
            .queryParam("query", "")
            .when()
            .get(BASE_URL + "search")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schema/disciplines-with-pagination.json"));
    }
}