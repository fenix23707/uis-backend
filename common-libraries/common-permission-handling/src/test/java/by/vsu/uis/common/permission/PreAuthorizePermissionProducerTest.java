package by.vsu.uis.common.permission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import by.kovzov.uis.security.dto.PermissionDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

class PreAuthorizePermissionProducerTest {

    private final RequestMappingHandlerMapping requestMappingHandlerMapping = mock(RequestMappingHandlerMapping.class);
    private final PreAuthorizePermissionProducer permissionProducer = new PreAuthorizePermissionProducer(requestMappingHandlerMapping);

    @ParameterizedTest
    @MethodSource("shouldCorrectlyParsePreAuthorizeValueTestData")
    void shouldCorrectlyParsePreAuthorizeValue(String value, PermissionDto expected) {
        PreAuthorize preAuthorize = mock(PreAuthorize.class);
        var method = mock(Method.class);
        var handlerMethod = mock(HandlerMethod.class);
        var handlerMethods = Map.of(mock(RequestMappingInfo.class), handlerMethod);

        when(preAuthorize.value()).thenReturn(value);
        when(method.isAnnotationPresent(nullable(Class.class))).thenReturn(true);
        when(method.getAnnotationsByType(PreAuthorize.class)).thenReturn(new PreAuthorize[]{preAuthorize});
        when(handlerMethod.getMethod()).thenReturn(method);
        when(requestMappingHandlerMapping.getHandlerMethods()).thenReturn(handlerMethods);

        List<PermissionDto> permissions = permissionProducer.producePermissions();

        assertThat(permissions)
            .usingRecursiveFieldByFieldElementComparator()
            .isEqualTo(List.of(expected));
    }

    static Stream<Arguments> shouldCorrectlyParsePreAuthorizeValueTestData() {
        return Stream.of(
            Arguments.of(
                "hasAuthority('USER_READ') or @authorizationService.hasSameId(#id)",
                new PermissionDto("user", "read")
            ),
            Arguments.of(
                "@authorizationService.hasSameId(#id) or hasAuthority('USER_READ')",
                new PermissionDto("user", "read")
            ),
            Arguments.of(
                "hasAuthority('USER_CREATE')",
                new PermissionDto("user", "create")
            ),
            Arguments.of(
                "hasAuthority('USER_MANAGE_ROLES')",
                new PermissionDto("user", "manage_roles")
            )
        );
    }
}