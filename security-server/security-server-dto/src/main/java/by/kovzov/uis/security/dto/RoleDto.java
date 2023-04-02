package by.kovzov.uis.security.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonInclude(Include.NON_NULL)
public class RoleDto {

    @Nullable
    Long id;

    @NotBlank
    String name;

    @NotNull
    List<Integer> permissionIds;
}
