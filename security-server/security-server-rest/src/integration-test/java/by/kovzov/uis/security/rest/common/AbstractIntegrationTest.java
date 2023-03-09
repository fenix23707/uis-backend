package by.kovzov.uis.security.rest.common;

import static io.restassured.RestAssured.given;

import by.kovzov.uis.security.dto.JwtAuthenticationDto;
import by.kovzov.uis.security.dto.LoginDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.MountableFile;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@ComponentScan(basePackages = "by.kovzov.uis.security.rest")
public abstract class AbstractIntegrationTest {

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine")
        .withCopyFileToContainer(MountableFile.forClasspathResource("schema.sql"), "/docker-entrypoint-initdb.d/1-schema.sql")
        .withCopyFileToContainer(MountableFile.forClasspathResource("data/sql/auth-user.sql"), "/docker-entrypoint-initdb.d/2-auth-user.sql");

    @LocalServerPort
    protected int localServerPort;

    @Autowired
    protected DataLoader dataLoader;

    protected static void overridePropertiesInternal(DynamicPropertyRegistry registry) {
        Startables.deepStart(postgreSQLContainer).join();

        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeEach
    public void setUpAbstractIntegrationTest() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.baseURI = "http://localhost:" + localServerPort;
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    protected String getJwtAccessToken() {
        LoginDto loginDto = LoginDto.builder()
            .username("test")
            .password("test")
            .build();
        return given()
            .when()
            .body(loginDto)
            .contentType(ContentType.JSON)
            .post("/api/security/tokens/create")
            .then()
            .statusCode(200)
            .extract()
            .response()
            .as(JwtAuthenticationDto.class).getAccessToken();
    }
}
