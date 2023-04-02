package by.kovzov.uis.academic.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TagDto {

    @Nullable
    Long id;

    @NotBlank
    String name;

    @Nullable
    TagDto parent;
}
