package by.kovzov.uis.domain.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank(message = "Username can not be blank.")
    private String username;

    @NotBlank(message = "Password can not be blank.")
    private String password;
}