package by.kovzov.uis.specialization.rest.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.MountableFile;

import io.restassured.RestAssured;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@ComponentScan(basePackages = "by.kovzov.uis.specialization.rest")
public abstract class AbstractIntegrationTest {
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine")
        .withCopyFileToContainer(MountableFile.forClasspathResource("schema.sql"),
            "/docker-entrypoint-initdb.d/1-schema.sql");

    @LocalServerPort
    protected int localServerPort;

    @DynamicPropertySource
    public static void setupTestContainers(DynamicPropertyRegistry registry) {
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

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
