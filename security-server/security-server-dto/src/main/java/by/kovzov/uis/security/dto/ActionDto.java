package by.kovzov.uis.security.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class ActionDto {

    Long id;
    String name;
}
