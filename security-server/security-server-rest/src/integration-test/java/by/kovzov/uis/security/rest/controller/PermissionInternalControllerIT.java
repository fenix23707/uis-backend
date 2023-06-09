package by.kovzov.uis.security.rest.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import by.kovzov.uis.security.dto.MethodDto;
import by.kovzov.uis.security.dto.PermissionDto;
import by.kovzov.uis.security.repository.api.PermissionRepository;
import by.kovzov.uis.security.repository.entity.Permission;
import by.kovzov.uis.security.rest.common.AbstractIntegrationTest;
import io.restassured.http.ContentType;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public class PermissionInternalControllerIT extends AbstractIntegrationTest {

    private static final String BASE_URL = "/api/internal/permissions/security-server";
    private static final String CLEAR_SQL = "/data/permissions/clear.sql";

    private static final RecursiveComparisonConfiguration LENIENT_CONFIG = RecursiveComparisonConfiguration.builder()
        .withIgnoreAllActualNullFields(true)
        .withIgnoreAllExpectedNullFields(true)
        .build();

    private static final Comparator<Permission> COMPARATOR = Comparator.comparing(Permission::getApplicationName)
        .thenComparing(Permission::getAction)
        .thenComparing(Permission::getScope);


    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        overridePropertiesInternal(registry);
    }

    @Autowired
    private PermissionRepository permissionRepository;

    @BeforeEach
    @AfterEach
    void tearDown() {
        executeSql(CLEAR_SQL);
    }

    @Test
    void shouldSaveAllNewPermissionsWithCorrespondingMethods() {
        var methods1 = Set.of(
            MethodDto.builder().description("unique method description 1").build(),
            MethodDto.builder().description("unique method description 2").build()
        );
        var methods2 = Set.of(
            MethodDto.builder().description("unique method description 3").build()
        );

        var body = List.of(
            PermissionDto.builder().scope("user").action("read").methods(methods1).build(),
            PermissionDto.builder().scope("").action("read").methods(methods2).build()
        );

        save(body);
        List<Permission> actual = permissionRepository.findAllWithMethods();

        assertThat(actual)
            .usingRecursiveComparison(LENIENT_CONFIG)
            .isEqualTo(body);
    }

    @TestFactory
    List<DynamicTest> permissionUpdateTests() {
        return List.of(
            DynamicTest.dynamicTest("shouldUpdateExistingPermission", () -> permissionUpdateTestMethod(
                "/data/permissions/shouldUpdateExistingPermission/given.sql",
                "data/permissions/shouldUpdateExistingPermission/update-body.json",
                "data/permissions/shouldUpdateExistingPermission/expected.json"
            )),
            DynamicTest.dynamicTest("shouldDeletePermission", () -> permissionUpdateTestMethod(
                "/data/permissions/shouldDeletePermission/given.sql",
                "data/permissions/shouldDeletePermission/update-body.json",
                "data/permissions/shouldDeletePermission/expected.json"
            )),
            DynamicTest.dynamicTest("shouldRenameExisting", () -> permissionUpdateTestMethod(
                "/data/permissions/shouldRenameExisting/given.sql",
                "data/permissions/shouldRenameExisting/update-body.json",
                "data/permissions/shouldRenameExisting/expected.json"
            )),
            DynamicTest.dynamicTest("shouldRenameExistingPermissionWithNewEndpoint", () -> permissionUpdateTestMethod(
                "/data/permissions/shouldRenameExistingPermissionWithNewEndpoint/given.sql",
                "data/permissions/shouldRenameExistingPermissionWithNewEndpoint/update-body.json",
                "data/permissions/shouldRenameExistingPermissionWithNewEndpoint/expected.json"
            ))
        );
    }

    @TestFactory
    List<DynamicTest> permissionRenameTests() {
        return List.of(
            DynamicTest.dynamicTest("shouldRenameOnePermission", () -> permissionUpdateTestMethod(
                "/data/permissions/rename/given.sql",
                "data/permissions/rename/one/new-state.json",
                "data/permissions/rename/one/expected.json"
            )),
            DynamicTest.dynamicTest("shouldRenameWithNewEndpoint", () -> permissionUpdateTestMethod(
                "/data/permissions/rename/given.sql",
                "data/permissions/rename/with-new-endpoints/new-state.json",
                "data/permissions/rename/with-new-endpoints/expected.json"
            )),
            DynamicTest.dynamicTest("shouldKeepOldOne", () -> permissionUpdateTestMethod(
                "/data/permissions/rename/given.sql",
                "data/permissions/rename/with-keep-old/new-state.json",
                "data/permissions/rename/with-keep-old/expected.json"
            )),
            DynamicTest.dynamicTest("shouldCreateNewOne", () -> permissionUpdateTestMethod(
                "/data/permissions/rename/given.sql",
                "data/permissions/rename/create-new-one/new-state.json",
                "data/permissions/rename/create-new-one/expected.json"
            ))
        );
    }

    private void permissionUpdateTestMethod(String initDataPath, String updateBodyPath, String expectedPath) {
        tearDown();
        executeSql(initDataPath);

        var body = dataLoader.loadJson(PermissionDto.class, updateBodyPath);
        save(body);

        var expected = dataLoader.loadJson(Permission.class, expectedPath);
        var actual = permissionRepository.findAllWithMethods();

        expected.sort(COMPARATOR);
        actual.sort(COMPARATOR);
        assertThat(actual).usingRecursiveComparison(LENIENT_CONFIG).isEqualTo(expected);
    }

    private void save(List<PermissionDto> body) {
        given()
            .body(body)
            .contentType(ContentType.JSON)
            .when()
            .post(BASE_URL)
            .then()
            .statusCode(200);
    }
}
