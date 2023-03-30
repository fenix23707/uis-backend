package by.kovzov.uis.security.dto;

import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class RoleDto {

    @Nullable
    Integer id;

    @NotBlank
    String name;

    @NotNull
    List<Integer> permissionIds;
}
