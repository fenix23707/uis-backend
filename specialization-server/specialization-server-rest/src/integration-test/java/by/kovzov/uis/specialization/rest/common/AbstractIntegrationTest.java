package by.kovzov.uis.specialization.rest.common;

import static io.restassured.RestAssured.given;

import java.util.List;

import by.kovzov.uis.security.dto.JwtAuthenticationDto;
import by.kovzov.uis.security.dto.LoginDto;
import io.restassured.RestAssured;
import io.restassured.authentication.OAuthSignature;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.MountableFile;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = "by.kovzov.uis.specialization.rest")
public abstract class AbstractIntegrationTest {

    static Network network = Network.newNetwork();

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine")
        .withNetwork(network)
        .withNetworkAliases("postgresql")
        .withCopyFileToContainer(MountableFile.forClasspathResource("schema.sql"), "/docker-entrypoint-initdb.d/1-schema.sql")
        .withCopyFileToContainer(MountableFile.forClasspathResource("data/sql/auth-user.sql"), "/docker-entrypoint-initdb.d/2-auth-user.sql");

    static GenericContainer<?> securityServerContainer = new GenericContainer<>("fenix23707/security-server:dev")
        .withNetwork(network)
        .withExposedPorts(8080)
        .dependsOn(postgreSQLContainer);

    @LocalServerPort
    protected int localServerPort;

    private static String securityServerUri;
    private static String jwtAccessToken;
    private static String baseUri;
    protected RequestSpecification requestSpecification;

    @Autowired
    protected DataLoader dataLoader;

    @PostConstruct
    public void init() {
        baseUri = "http://localhost:" + localServerPort;
    }

    protected static void overridePropertiesInternal(DynamicPropertyRegistry registry) {
        postgreSQLContainer.start();
        securityServerContainer.setEnv(List.of(
            "SPRING_DATASOURCE_URL=jdbc:postgresql://postgresql/test",
            "SPRING_DATASOURCE_USERNAME=" + postgreSQLContainer.getUsername(),
            "SPRING_DATASOURCE_PASSWORD=" + postgreSQLContainer.getPassword()
        ));
        securityServerContainer.start();
        securityServerContainer.waitingFor(Wait.forHttp("/api/security/jwk-set-uri"));
        securityServerUri = "http://%s:%s".formatted(securityServerContainer.getHost(), securityServerContainer.getFirstMappedPort());

        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("jwk-set-uri", () -> "%s/api/security/jwk-set-uri".formatted(securityServerUri));
    }


    @BeforeAll
    static void beforeAll() {
        jwtAccessToken = getJwtAccessToken();
    }

    @BeforeEach
    public void setUpAbstractIntegrationTest() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        requestSpecification = given()
            .baseUri(baseUri)
            .auth().oauth2(jwtAccessToken, OAuthSignature.HEADER);
    }

    @AfterAll
    static void afterAll() {
        securityServerContainer.stop();
        postgreSQLContainer.stop();
    }

    private static String getJwtAccessToken() {
        LoginDto loginDto = LoginDto.builder().username("test").password("test").build();
        return given()
            .baseUri(securityServerUri)
            .body(loginDto)
            .contentType(ContentType.JSON)
            .when()
            .post("/api/security/tokens/create")
            .then()
            .statusCode(200)
            .extract()
            .response()
            .as(JwtAuthenticationDto.class)
            .getAccessToken();
    }
}
