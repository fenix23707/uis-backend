package by.kovzov.uis.security.rest.controller;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;

import java.util.List;

import by.kovzov.uis.security.repository.api.UserRepository;
import by.kovzov.uis.security.repository.entity.User;
import by.kovzov.uis.security.rest.common.AbstractIntegrationTest;
import io.restassured.specification.RequestSpecification;
import lombok.Setter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
public class UserControllerIT extends AbstractIntegrationTest {

    private static final String BASE_URL = "/api/users/";

    @Autowired
    private UserRepository userRepository;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        overridePropertiesInternal(registry);
    }

    private List<User> users;

    private RequestSpecification requestSpecification;

    @BeforeAll
    void beforeAll() {
        userRepository.saveAll(dataLoader.loadJson(User.class, "data/json/users.json"));
        this.users = userRepository.findAll();
    }

    @BeforeEach
    void beforeEach() {
        this.requestSpecification = given()
            .auth()
            .oauth2(getJwtAccessToken());
    }

    @Test
    void searchShouldReturnValidResponse() {
       requestSpecification.when()
            .get(BASE_URL + "search")
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schema/users-with-pagination.json"));
    }

    @ParameterizedTest
    @CsvSource({
        "US, 2",
    })
    void searchShouldReturnSizeAccordingToFilters(String username, int expectedSize) {
        requestSpecification.when()
            .param("username", username)
            .param("size", users.size())
            .get(BASE_URL + "search")
            .then()
            .statusCode(200)
            .body("totalElements", is(expectedSize));
    }

}
