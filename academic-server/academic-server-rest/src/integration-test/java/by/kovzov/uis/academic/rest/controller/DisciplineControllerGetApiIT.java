package by.kovzov.uis.academic.rest.controller;

import static java.text.MessageFormat.format;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;

import by.kovzov.uis.academic.repository.api.DisciplineRepository;
import by.kovzov.uis.academic.repository.entity.Discipline;
import by.kovzov.uis.academic.rest.common.AbstractIntegrationTest;
import lombok.Setter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@Setter
@TestInstance(Lifecycle.PER_CLASS)
class DisciplineControllerGetApiIT extends AbstractIntegrationTest {

    private static final String BASE_URL = "/api/disciplines/";

    @Autowired
    private DisciplineRepository disciplineRepository;

    private List<Discipline> disciplines;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        overridePropertiesInternal(registry);
    }

    @BeforeAll
    void setUp() {
        disciplines = dataLoader.loadJson(Discipline.class, "data/json/disciplines.json");
        disciplineRepository.saveAllAndFlush(disciplines);
    }

    @Test
    void getByIdReturnValidResponse() {
        int id = 1;

        requestSpecification
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

        requestSpecification
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

        requestSpecification
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
        requestSpecification
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
        requestSpecification
            .queryParam("query", "")
            .when()
            .get(BASE_URL + "search")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schema/disciplines-with-pagination.json"));
    }
}
