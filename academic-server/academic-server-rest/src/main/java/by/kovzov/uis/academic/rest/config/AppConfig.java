package by.kovzov.uis.academic.rest.config;

import static org.apache.commons.lang3.Validate.notBlank;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    public WebClient securityWebClient(@Value("${uis.security-server-url}")String securityUrl) {
        return WebClient.builder()
            .baseUrl(notBlank(securityUrl))
            .build();
    }
}
