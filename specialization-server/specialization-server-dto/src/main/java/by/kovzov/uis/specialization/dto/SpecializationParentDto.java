package by.kovzov.uis.specialization.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class SpecializationParentDto {
    Long id;
    String name;
    String shortName;
    String cipher;
    boolean hasChildren;
}
