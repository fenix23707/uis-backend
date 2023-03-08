package by.kovzov.uis.specialization.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class SpecializationDto {
    Long id;
    String name;
    String shortName;
    String cipher;
    Long parentId;
    boolean hasChildren;
}
