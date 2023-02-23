package by.kovzov.uis.specialization.rest.controller;

import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import static java.text.MessageFormat.format;

import static io.restassured.RestAssured.form;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;

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

    @ParameterizedTest
    @MethodSource("invalidJsonSchemas")
    void createShouldReturnErrorIfJsonSchemaIsNotValid(String json) {
        given()
            .contentType(ContentType.JSON)
            .body(json)
            .when()
            .post(BASE_URL)
            .then()
            .statusCode(400);
    }

    @Test
    void updateShouldReturnValidJsonSchema() {
        Discipline entity = buildDiscipline(null);
        long id = disciplineRepository.save(entity).getId();
        String path = format("{0}/{1}", BASE_URL, id);

        given()
            .contentType(ContentType.JSON)
            .body(buildDiscipline(id))
            .when()
            .put(path)
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schema/discipline.json"));
    }

    @Test
    void updateShouldAcceptPartOfDocument() {
        Discipline entity = buildDiscipline(null);
        long id = disciplineRepository.save(entity).getId();
        String path = format("{0}/{1}", BASE_URL, id);
        String json = """ 
            {
                "name": "asfa"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(json)
            .when()
            .put(path)
            .then()
            .statusCode(200);
    }

    @Test
    void updateShouldReturnErrorIfIdNotExists() {
        long notExistedId = 0;
        String path = format("{0}/{1}", BASE_URL, notExistedId);

        given()
            .contentType(ContentType.JSON)
            .body(buildDiscipline(notExistedId))
            .when()
            .put(path)
            .then()
            .statusCode(404)
            .body("message", not(blankOrNullString()))
            .body("path", is(path));
    }

    @Test
    void updateShouldReturnErrorIfFieldIsNotUnique() {
        String existingName = "existing name";
        Discipline entity = buildDiscipline(null);
        entity.setName(existingName);
        disciplineRepository.save(entity);
        Discipline discipline = buildDiscipline(null);
        long id = disciplineRepository.save(discipline).getId();
        String path = format("{0}/{1}", BASE_URL, id);
        String json = """
                {
                    "name": %s
                }
            """.formatted(existingName);

        given()
            .contentType(ContentType.JSON)
            .body(json)
            .when()
            .put(path)
            .then()
            .statusCode(409)
            .body("message", not(blankOrNullString()))
            .body("path", is(path));
    }

    private static List<String> invalidJsonSchemas() {
        return List.of(
            """ 
                {
                    "name": "asfafs",
                    "shortName": ""
                }
                """,
            """ 
                {
                    "name": "asfafs"
                }
                """
        );
    }

    private Discipline buildDiscipline(Long id) {
        Discipline discipline = new Discipline();
        discipline.setId(id);
        discipline.setName("test discipline name");
        discipline.setShortName("test discipline short name");
        return discipline;
    }
}
