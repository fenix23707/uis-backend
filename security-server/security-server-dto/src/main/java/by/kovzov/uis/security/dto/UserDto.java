package by.kovzov.uis.security.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(Include.NON_NULL)
public class UserDto {

    Long id;
    String username;
    LocalDateTime lastActivity;
    LocalDateTime creationTime;
    Set<RoleDto> roles;
}
