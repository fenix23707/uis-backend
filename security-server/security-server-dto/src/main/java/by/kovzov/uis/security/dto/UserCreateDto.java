package by.kovzov.uis.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserCreateDto {

    @NotBlank
    String username;

    @NotBlank
    String password;
}
