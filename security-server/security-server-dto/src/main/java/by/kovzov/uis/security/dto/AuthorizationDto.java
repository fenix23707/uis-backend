package by.kovzov.uis.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthorizationDto {

    @NotBlank
    String url;

    @NotNull
    HttpMethod httpMethod;

    @NotBlank
    String accessToken; //TODO add token verification
}
