package by.kovzov.uis.academic.dto;

import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class DisciplineDto {

    @Nullable
    Long id;

    @NotBlank(message = "Name can not be blank")
    String name;

    @NotBlank(message = "Short name can not be blank")
    String shortName;

    List<TagDto> tags;
}
