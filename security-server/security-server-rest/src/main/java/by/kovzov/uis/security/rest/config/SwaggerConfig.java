package by.kovzov.uis.security.rest.config;

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

    public static final String Security_SCHEME_NAME = "bearerSecurity";

    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI()
            .info(info())
            .addSecurityItem(new SecurityRequirement().addList(Security_SCHEME_NAME))
            .components(new Components().addSecuritySchemes(Security_SCHEME_NAME, SecurityScheme()));
    }

    private SecurityScheme SecurityScheme() {
        return new SecurityScheme()
            .name(Security_SCHEME_NAME)
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
