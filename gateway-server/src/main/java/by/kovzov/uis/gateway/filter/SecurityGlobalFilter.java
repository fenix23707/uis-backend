package by.kovzov.uis.gateway.filter;

import by.kovzov.uis.security.dto.AuthorizationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SecurityGlobalFilter implements GlobalFilter {

    private final WebClient securityWebClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        String authorizationHeader = request.getHeaders().getFirst("");

        String accessToken = extractAccessToken(authorizationHeader);

        AuthorizationDto authorizationDto = new AuthorizationDto(request.getPath().value(), accessToken);
        return securityWebClient.post()
            .uri("/authorize")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(authorizationDto)
            .exchange()
            .flatMap(response -> {
                if (response.statusCode().is2xxSuccessful()) {
                    return chain.filter(exchange);
                } else {
                    return response.bodyToMono(String.class)
                        .flatMap(errorBody -> {
                            ServerHttpResponse httpResponse = exchange.getResponse();
                            httpResponse.setStatusCode(response.statusCode());
                            httpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                            DataBuffer buffer = httpResponse.bufferFactory().wrap(errorBody.getBytes());
                            return httpResponse.writeWith(Mono.just(buffer));
                        });
                }
            });
    }

    private String extractAccessToken(String authorizationHeader) {
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
