package by.kovzov.uis.academic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class SpecializationDto {
    Long id;

    @NotBlank(message = "Name can not be blank")
    String name;

    @NotBlank(message = "Short name can not be blank")
    String shortName;

    @NotBlank(message = "Cipher can not be blank")
    String cipher;
    Long parentId;
    boolean hasChildren;
}
