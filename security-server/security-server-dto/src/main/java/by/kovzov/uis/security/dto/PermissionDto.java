package by.kovzov.uis.security.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PermissionDto {

    Integer id;
    String scope;
    String action;
}
