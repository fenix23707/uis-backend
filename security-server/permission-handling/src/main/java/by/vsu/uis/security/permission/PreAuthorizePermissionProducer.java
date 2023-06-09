package by.vsu.uis.security.permission;

import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.kovzov.uis.security.dto.MethodDto;
import by.kovzov.uis.security.dto.PermissionDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Service
public class PreAuthorizePermissionProducer implements PermissionProducer {

    private static final Pattern PATTERN = Pattern.compile(".*hasAuthority\\('([A-Z]+)_([A-Z_]+)'\\).*");

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public PreAuthorizePermissionProducer(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @Override
    public List<PermissionDto> producePermissions() {
        Map<RequestMappingInfo, HandlerMethod> mappings = requestMappingHandlerMapping.getHandlerMethods();
        return mappings.values().stream()
            .map(this::buildPermission)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(groupingBy(
                dto ->  PermissionDto.of(dto.getScope(), dto.getAction()),
                flatMapping(it -> it.getMethods().stream(), toSet())
            )).entrySet().stream()
            .map(entry -> entry.getKey().toBuilder()
                .methods(entry.getValue())
                .build()
            )
            .toList();
    }

    private Optional<PermissionDto> buildPermission(HandlerMethod handlerMethod) {
        PreAuthorize preAuthorize = handlerMethod.getMethodAnnotation(PreAuthorize.class);
        if (Objects.isNull(preAuthorize)) {
            return Optional.empty();
        }

        Matcher matcher = PATTERN.matcher(preAuthorize.value());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        var method = new MethodDto(handlerMethod.toString());
        var permission = PermissionDto.builder()
            .scope(matcher.group(1).toLowerCase())
            .action(matcher.group(2).toLowerCase())
            .methods(Set.of(method))
            .build();
        return Optional.of(permission);
    }
}
