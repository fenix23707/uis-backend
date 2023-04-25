package by.kovzov.uis.security.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class RoleEditDto {

    @NotBlank(message = "name must not be blank")
    String name;

    @NotNull(message = "permission ids must not be blank")
    List<Long> permissionIds;
}
