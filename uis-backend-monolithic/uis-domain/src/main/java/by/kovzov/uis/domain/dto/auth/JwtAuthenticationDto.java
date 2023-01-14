package by.kovzov.uis.domain.dto.auth;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

@Getter
@Builder
public class JwtAuthenticationDto {

    private Long id;
    private String accessToken;
    private String refreshToken;

    @Default
    private String tokenType = "Bearer";
}
