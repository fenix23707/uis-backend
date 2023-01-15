package by.kovzov.uis.security.domain.dto;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;

@Getter
@Builder
public class JwtsecurityenticationDto {

    private Long id;
    private String accessToken;
    private String refreshToken;

    @Default
    private String tokenType = "Bearer";
}
