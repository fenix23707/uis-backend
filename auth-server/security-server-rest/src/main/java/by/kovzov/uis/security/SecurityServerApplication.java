package by.kovzov.uis.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import by.kovzov.uis.security.rest.security.RsaKeyProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication(scanBasePackages = {"by.kovzov.uis.security"})
@EnableConfigurationProperties(RsaKeyProperties.class)
@OpenAPIDefinition
public class SecurityServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityServerApplication.class, args);
    }
}
