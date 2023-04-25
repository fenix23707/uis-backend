package by.kovzov.uis.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class PermissionDto {

    @NotBlank(message = "scope must not be blank")
    String scope;

    @NotBlank(message = "action must not be blank")
    String action;
}
