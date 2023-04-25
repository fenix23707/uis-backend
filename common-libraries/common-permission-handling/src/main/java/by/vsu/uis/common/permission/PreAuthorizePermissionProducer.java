package by.vsu.uis.common.permission;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import by.kovzov.uis.security.dto.PermissionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Service
@RequiredArgsConstructor
public class PreAuthorizePermissionProducer implements PermissionProducer {

    private static final Pattern PATTERN = Pattern.compile("hasAuthority\\('([A-Z]+)_([A-Z_]+)'\\)");

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public List<PermissionDto> producePermissions() {
        Map<RequestMappingInfo, HandlerMethod> mappings = requestMappingHandlerMapping.getHandlerMethods();
        return mappings.values().stream()
            .map(HandlerMethod::getMethod)
            .filter(method -> method.isAnnotationPresent(PreAuthorize.class))
            .map(method -> method.getAnnotationsByType(PreAuthorize.class))
            .flatMap(Stream::of)
            .map(PreAuthorize::value)
            .map(PATTERN::matcher)
            .filter(Matcher::matches)
            .map(this::buildPermission)
            .toList();
    }

    private PermissionDto buildPermission(Matcher matcher) {
        return PermissionDto.builder()
            .scope(matcher.group(1).toLowerCase())
            .action(matcher.group(2).toLowerCase())
            .build();
    }
}
