package by.kovzov.uis.specialization.rest.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;

import by.kovzov.uis.specialization.repository.api.SpecializationRepository;
import by.kovzov.uis.specialization.repository.entity.Specialization;
import by.kovzov.uis.specialization.rest.common.AbstractIntegrationTest;
import lombok.Setter;

@Setter
@TestInstance(Lifecycle.PER_CLASS)
public class SpecializationControllerGetApiIT extends AbstractIntegrationTest {

    @Autowired
    private SpecializationRepository specializationRepository;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        overridePropertiesInternal(registry);
    }

    private List<Specialization> specializations;
    @BeforeAll
    void setUp() {
        specializations = dataLoader.loadJson(Specialization.class, "data/json/specializations.json");
        specializationRepository.saveAll(specializations);
    }

    @Test
    void searchReturnAllIfQueryIsEmpty() {
        given()
            .when()
            .queryParam("query", "")
            .queryParam("size", specializations.size())
            .get("/api/specializations/search")
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
        given()
            .queryParam("query", query)
            .queryParam("size", specializations.size())
            .when()
            .get("/api/specializations/search")
            .then()
            .statusCode(200)
            .body("content", hasSize(expectedSize))
            .body("totalElements", is(expectedSize));
    }
}
