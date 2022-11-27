package by.kovzov.uis.domain.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.Getter;

@Data
public class LoginRequest {

    @NotBlank(message = "${validation.error.empty}")
    private String username;

    @NotBlank(message = "${validation.error.empty}")
    private String password;
}
