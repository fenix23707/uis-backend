package by.kovzov.uis.security.dto;

import java.util.Collections;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class PermissionDto {

    @NotBlank(message = "scope can not be blank")
    String scope;

    @NotBlank(message = "action can not be blank")
    String action;

    @NotEmpty(message = "methods can not be empty")
    Set<MethodDto> methods;

    public static PermissionDto of(String scope, String action) {
        return PermissionDto.builder()
            .scope(scope)
            .action(action)
            .methods(Collections.emptySet())
            .build();
    }
}
