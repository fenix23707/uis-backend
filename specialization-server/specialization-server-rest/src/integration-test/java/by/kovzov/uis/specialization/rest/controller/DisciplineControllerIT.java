package by.kovzov.uis.specialization.rest.controller;

import static java.text.MessageFormat.format;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.stream.Stream;

import by.kovzov.uis.specialization.repository.api.DisciplineRepository;
import by.kovzov.uis.specialization.repository.entity.Discipline;
import by.kovzov.uis.specialization.rest.common.AbstractIntegrationTest;
import io.restassured.http.ContentType;
import lombok.Setter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@Setter
@TestInstance(Lifecycle.PER_CLASS)
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
        requestSpecification
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
        requestSpecification
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
        requestSpecification
            .contentType(ContentType.JSON)
            .body(json)
            .when()
            .post(BASE_URL)
            .then()
            .statusCode(400);
    }

    @Test
    void updateShouldReturnValidJsonSchema() {
        String path = generateUpdatePath();

        requestSpecification
            .contentType(ContentType.JSON)
            .body(buildDiscipline(null))
            .when()
            .put(path)
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schema/discipline.json"));
    }

    @ParameterizedTest
    @MethodSource("invalidJsonSchemas")
    void updateShouldReturnErrorIfJsonSchemaIsNotValid(String json) {
        String path = generateUpdatePath();

        requestSpecification
            .contentType(ContentType.JSON)
            .body(json)
            .when()
            .put(path)
            .then()
            .statusCode(400)
            .body("message", not(blankOrNullString()))
            .body("path", is(path));
    }

    @Test
    void updateShouldReturnErrorIfIdNotExists() {
        long notExistedId = 0;
        String path = format("{0}/{1}", BASE_URL, notExistedId);

        requestSpecification
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
        String json = """
                {
                    "name": "%s",
                    "shortName": "asf"
                }
            """.formatted(existingName);
        String path = generateUpdatePath();

        requestSpecification
            .contentType(ContentType.JSON)
            .body(json)
            .when()
            .put(path)
            .then()
            .statusCode(409)
            .body("message", not(blankOrNullString()))
            .body("path", is(path));
    }

    static Stream<String> invalidJsonSchemas() {
        return Stream.of(
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

    private String generateUpdatePath() {
        Discipline entity = buildDiscipline(null);
        entity.setName("unique name");
        entity.setShortName("unique short name");
        long id = disciplineRepository.save(entity).getId();
        return format("{0}/{1}", BASE_URL, id);
    }

    private Discipline buildDiscipline(Long id) {
        Discipline discipline = new Discipline();
        discipline.setId(id);
        discipline.setName("test discipline name");
        discipline.setShortName("test discipline short name");
        return discipline;
    }
}
