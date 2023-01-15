package by.kovzov.uis.security.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenDto {

    @NotBlank(message = "Refresh token can not be blank.")
    private String refreshToken;
}
