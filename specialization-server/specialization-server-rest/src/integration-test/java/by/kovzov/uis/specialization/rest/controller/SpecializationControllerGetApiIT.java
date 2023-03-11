package by.kovzov.uis.specialization.rest.controller;

import static java.text.MessageFormat.format;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;

import by.kovzov.uis.specialization.repository.api.SpecializationRepository;
import by.kovzov.uis.specialization.repository.entity.Specialization;
import by.kovzov.uis.specialization.rest.common.AbstractIntegrationTest;
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
public class SpecializationControllerGetApiIT extends AbstractIntegrationTest {

    private static final String BASE_URL = "/api/specializations/";

    @Autowired
    private SpecializationRepository specializationRepository;

    private List<Specialization> specializations;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        overridePropertiesInternal(registry);
    }

    @BeforeAll
    void setUp() {
        specializations = dataLoader.loadJson(Specialization.class, "data/json/specializations.json");
        specializationRepository.saveAll(specializations);
    }

    @Test
    void getByIdShouldReturnValidResponse() {
        int id = 1;

        requestSpecification
            .when()
            .get(BASE_URL + id)
            .then()
            .statusCode(200)
            .body("id", is(id))
            .body("name", is("Applied Computer Science"))
            .body("shortName", is("ACS"))
            .body("cipher", is("PI cipher"));
    }

    @Test
    void getByIdShouldReturnNotFoundIfIdNotExist() {
        int id = 999999;

        requestSpecification
            .when()
            .get(BASE_URL + id)
            .then()
            .statusCode(404)
            .body("message", is(format("Specialization with id = {0} not found.", id)))
            .body("path", is(BASE_URL + id));
    }

    @Test
    void getByIdShouldReturnValidJsonSchema() {
        int id = 1;

        requestSpecification
            .when()
            .get(BASE_URL + id)
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schema/specialization.json"));
    }

    @Test
    void getByIdShouldReturnParentId() {
        int id = 2;

        requestSpecification
            .when()
            .get(BASE_URL + id)
            .then()
            .statusCode(200)
            .body("parentId", is(1));
    }

    @Test
    void searchReturnAllIfQueryIsEmpty() {
        requestSpecification
            .queryParam("query", "")
            .queryParam("size", specializations.size())
            .when()
            .get(BASE_URL + "search")
            .then()
            .statusCode(200)
            .body("content", hasSize(specializations.size()))
            .body("totalElements", is(specializations.size()));
    }

    @ParameterizedTest
    @CsvSource({
        "'d ',      4",
        "cipher,    7",
        "MATH,      1",
        "asfa,      0"
    })
    void searchReturnCountAccordingToQuery(String query, int expectedSize) {
        requestSpecification
            .queryParam("query", query)
            .queryParam("size", specializations.size())
            .when()
            .get(BASE_URL + "search")
            .then()
            .statusCode(200)
            .body("content", hasSize(expectedSize))
            .body("totalElements", is(expectedSize));
    }
}
