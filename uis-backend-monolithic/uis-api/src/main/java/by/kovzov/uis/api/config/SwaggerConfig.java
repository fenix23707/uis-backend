package by.kovzov.uis.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    public static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI()
            .info(info())
            .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
            .components(new Components().addSecuritySchemes(SECURITY_SCHEME_NAME, securityScheme()));
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
            .name(SECURITY_SCHEME_NAME)
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT");
    }

    private Info info() {
        return new Info()
            .title("University Information System")
            .contact(contact());
    }

    private Contact contact() {
        return new Contact()
            .name("Vlad")
            .email("fenix23707@gmail.com")
            .url("https://github.com/fenix23707/uis-backend");
    }
}
