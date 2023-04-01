package by.kovzov.uis.academic.rest.controller;


import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.stream.Stream;

import by.kovzov.uis.academic.dto.SpecializationRequestDto;
import by.kovzov.uis.academic.repository.api.SpecializationRepository;
import by.kovzov.uis.academic.repository.entity.Specialization;
import by.kovzov.uis.academic.rest.common.AbstractIntegrationTest;
import by.kovzov.uis.academic.service.mapper.SpecializationMapper;
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
class SpecializationControllerIT extends AbstractIntegrationTest {

    private static final String BASE_URL = "/api/specializations";

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private SpecializationMapper specializationMapper;

    private Long specializationId;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        overridePropertiesInternal(registry);
    }

    @AfterEach
    void tearDown() {
        specializationRepository.deleteAll();
    }

    @Test
    void createShouldReturnValidJson() {
        requestSpecification
            .contentType(ContentType.JSON)
            .body(buildSpecializationRequestDto())
            .when()
            .post(BASE_URL)
            .then()
            .statusCode(201)
            .body("id", not(emptyOrNullString()))
            .body(matchesJsonSchemaInClasspath("schema/specialization.json"));
    }

    @Test
    void createShouldReturnErrorIfSpecializationAlreadyExist() {
        Specialization specialization = buildSpecialization(null);
        specializationRepository.save(specialization);
        requestSpecification
            .contentType(ContentType.JSON)
            .body(specializationMapper.toDto(specialization))
            .when()
            .post(BASE_URL)
            .then()
            .statusCode(409)
            .body("message", not(blankOrNullString()))
            .body("path", is(BASE_URL));
    }

    @ParameterizedTest
    @MethodSource("invalidSpecializationRequestDtos")
    void createShouldReturnErrorIfDtoIsNotValid(SpecializationRequestDto dto) {
        requestSpecification
            .contentType(ContentType.JSON)
            .body(dto)
            .when()
            .post(BASE_URL)
            .then()
            .statusCode(400);
    }

    @Test
    void updateShouldReturnValidJsonSchema() {
        String path = generateUpdatePath();
        SpecializationRequestDto dto = buildSpecializationRequestDto().toBuilder()
            .parentId(specializationId)
            .build();

        requestSpecification
            .contentType(ContentType.JSON)
            .body(dto)
            .when()
            .put(path)
            .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schema/specialization.json"));
    }

    @ParameterizedTest
    @MethodSource("invalidSpecializationRequestDtos")
    void updateShouldReturnErrorIfDtoIsNotValid(SpecializationRequestDto dto) {
        String path = generateUpdatePath();

        requestSpecification
            .contentType(ContentType.JSON)
            .body(dto)
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
        String path = "%s/%s".formatted(BASE_URL, notExistedId);

        requestSpecification
            .contentType(ContentType.JSON)
            .body(buildSpecializationRequestDto())
            .when()
            .put(path)
            .then()
            .statusCode(404)
            .body("message", not(blankOrNullString()))
            .body("path", is(path));
    }

    @Test
    void updateShouldReturnErrorIfFieldAreNotUnique() {
        String existingName = "existing name";
        Specialization entity = buildSpecialization(null);
        entity.setName(existingName);
        specializationRepository.save(entity);
        SpecializationRequestDto dto = buildSpecializationRequestDto().toBuilder()
            .name(existingName)
            .build();
        String path = generateUpdatePath();

        requestSpecification
            .contentType(ContentType.JSON)
            .body(dto)
            .when()
            .put(path)
            .then()
            .statusCode(409)
            .body("message", not(blankOrNullString()))
            .body("path", is(path));
    }

    static Stream<SpecializationRequestDto> invalidSpecializationRequestDtos() {
        return Stream.of(
            SpecializationRequestDto.builder().name("  ").build(),
            SpecializationRequestDto.builder().build(),
            SpecializationRequestDto.builder().name("").shortName("").cipher("").build()
        );
    }

    private String generateUpdatePath() {
        Specialization specialization = buildSpecialization(null);
        specialization.setName("unique name");
        specialization.setShortName("unique shortName");
        specialization.setCipher("unique cipher");
        specializationId = specializationRepository.save(specialization).getId();
        return "%s/%s".formatted(BASE_URL, specializationId);
    }

    private Specialization buildSpecialization(Long id) {
        Specialization specialization = new Specialization();
        specialization.setId(id);
        specialization.setName("specialization test name");
        specialization.setShortName("specialization test short name");
        specialization.setCipher("test cipher");
        return specialization;
    }

    private SpecializationRequestDto buildSpecializationRequestDto() {
        return SpecializationRequestDto.builder()
            .name("specialization dto test name")
            .shortName("specialization dto test short name")
            .cipher("specialization dto test cipher")
            .build();
    }
}
