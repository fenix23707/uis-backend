package by.kovzov.uis.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
public class LoginDto {

    @NotBlank(message = "Username can not be blank.")
    String username;

    @NotBlank(message = "Password can not be blank.")
    String password;
}
