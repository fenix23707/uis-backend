package by.kovzov.uis.specialization.rest.controller;

import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import by.kovzov.uis.specialization.repository.api.DisciplineRepository;
import by.kovzov.uis.specialization.repository.entity.Discipline;
import by.kovzov.uis.specialization.rest.common.AbstractIntegrationTest;
import io.restassured.http.ContentType;
import lombok.Setter;

@Setter
public class DisciplineControllerIT extends AbstractIntegrationTest {

    private static final String BASE_URL = "/api/disciplines";

    @Autowired
    private DisciplineRepository disciplineRepository;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        overridePropertiesInternal(registry);
    }

    @AfterEach
    void tearDown() {
        disciplineRepository.deleteAll();
    }

    @Test
    void createShouldReturnValidJson() {
        given()
            .contentType(ContentType.JSON)
            .body(buildDiscipline(null))
            .when()
            .post(BASE_URL)
            .then()
            .statusCode(201)
            .body("id", not(emptyOrNullString()))
            .body(matchesJsonSchemaInClasspath("schema/discipline.json"));
    }

    @Test
    void createShouldReturnErrorIfDisciplineAlreadyExist() {
        Discipline discipline = buildDiscipline(null);
        disciplineRepository.save(discipline);
        given()
            .contentType(ContentType.JSON)
            .body(discipline)
            .when()
            .post(BASE_URL)
            .then()
            .statusCode(409)
            .body("message", not(blankOrNullString()))
            .body("path", is(BASE_URL));
    }

    @Test
    void createShouldReturnErrorIfJsonSchemaIsNotValid() {
        given()
            .contentType(ContentType.JSON).body(""" 
                {
                    "name": "",
                    "shortName": ""
                }
                """)
            .when()
            .post(BASE_URL)
            .then()
            .statusCode(400);
    }

    private Discipline buildDiscipline(Long id) {
        Discipline discipline = new Discipline();
        discipline.setId(id);
        discipline.setName("test discipline name");
        discipline.setShortName("test discipline short name");
        return discipline;
    }
}
