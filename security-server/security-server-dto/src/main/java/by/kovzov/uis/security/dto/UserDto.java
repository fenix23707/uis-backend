package by.kovzov.uis.security.dto;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {

    Long id;
    String username;
    LocalDateTime lastActivity;
    LocalDateTime creationTime;
    Set<RoleDto> roles;
}
