package by.kovzov.uis.domain.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank(message = "${validation.error.empty}")
    private String username;

    @NotBlank(message = "${validation.error.empty}")
    private String password;
}
