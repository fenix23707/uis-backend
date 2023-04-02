package by.kovzov.uis.academic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(Include.NON_NULL)
public class TagDto {

    @Nullable
    Long id;

    @NotBlank
    String name;

    @Nullable
    TagDto parent;
}
