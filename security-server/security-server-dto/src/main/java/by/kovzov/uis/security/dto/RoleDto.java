package by.kovzov.uis.security.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoleDto {

    Integer id;
    String name;
}
