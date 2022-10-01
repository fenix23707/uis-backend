package by.kovzov.uis.domain.dto.response;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Builder
public class JwtAuthenticationResponse {

    private Long id;
    private String accessToken;
    private String refreshToken;

    @Default
    private String tokenType = "Bearer";
}
