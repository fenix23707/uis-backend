package by.kovzov.uis.academic.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class TagRequestDto {

    @NotBlank
    String name;

    @Nullable
    Long parentId;
}
